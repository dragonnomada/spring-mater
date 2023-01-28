# Java Spring Master Class 2

* ¿Qué son los microservicios? Servidores de recursos con peticiones y respuestas cortas
* ¿Qué es Java? Un lenguaje para crear objetos que retienen estados
* ¿Qué es Java Spring? Un conjunto de herramientas para crear aplicaciones seguras
* ¿Qué es Java Spring Web? Herramientas para crear un servidor en la nube (aplicación web)
* ¿Qué es un API? Transacciones coordinadas en múltiples recursos
* ¿Qué es REST? Estructura de las peticiones
* ¿Qué es RESTFul? Estructura las peticiones con métodos más semánticos
* ¿Qué es @RestController? Clase que define métodos para mapear rutas en respuestas
* ¿Qué es @GetMapping? Mapeo tipo GET de una ruta a un valor (simple o complejo)
* ¿Qué es @PostMapping? Mapeo tipo POST de una ruta a un valor (simple o complejo)
* ¿Qué es @PathVariable("`<name>`")? Es recuperar un valor variable desde la url en una posición dada
* ¿Qué es @PutMapping? Mapeo tipo PUT de una ruta a un valor (simple o complejo)
* ¿Qué es @DeleteMapping? Mapeo tipoDELETE de una ruta a un valor (simple o complejo)
* ¿Qué es una Entidad?  Una clase que representa un modelo de datos
* ¿Qué es @RequestBody? La forma de recuperar los datos de una entidad en el API
* ¿Qué es un Servicio? Una clase que define las operaciones y transacciones lógicas


## Práctica 3

> Generar un servidor de reportes

- Recibir un archivo de texto
- Procesar el archivo de texto
- Generar un reporte de texto HTML
- Cosumir el reporte de texto HTML

## Práctica 4

> Diseñar un servidor de captura y consumo de productos

- Generar la entidad producto de Hibernate
- Generar el repositorio de productos de Hibernate
- Crear un nuevo producto
- Consultar los productos creados
- (Opcional) Actualizar un producto
- (Opcional) Eliminar un producto

## Códigos importantes

> `DemoApi.java`

```java
package com.nomadacode.s200_repaso_rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {

	@GetMapping("/api/demo/test") // GET /api/demo/test -> test() -> String
	public String test(
			@RequestParam(
					name = "nombre",
					required = true, 
					defaultValue = "Anónimo"
			) 
			String nombre, 
			@RequestParam("edad") 
			Integer edad
	) {
		
		System.out.println(nombre);
		
		return String.format("Hola %s, tienes %d años", nombre, edad);
		
	}
	
	@GetMapping("/api/demo/saludar")
	public List<String> saludar(
			@RequestParam(
					name = "mensaje",
					defaultValue = "Sin mensaje"
			)
			String mensaje,
			@RequestParam(
					name = "veces", 
					defaultValue = "1"
			)
			int veces
	) {
		
		List<String> saludos = new ArrayList<>();
		
		for (int i = 1; i <= veces; i++) {
			saludos.add(String.format("Hola, tu mensaje es: %s", mensaje));
		}
		
		return saludos;
		
	}
	
}
```

> `UploadApi.java`

```java
package com.nomadacode.s201_upload_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadApi {

	int quota = 0;
	int max_quota = 1 * 1024 * 1024 * 40; // 40MB 
	
	@PostMapping("/upload")
	public ResponseEntity<String> upload(
			@RequestParam("file") MultipartFile file, 
			@RequestParam(name = "token", defaultValue = "") String token
	) {
		
		if (!token.equals("abc123")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Acceso denegado: Token no válido"); // UNAUTHORIZED 401 -> String
		}
		
		if (quota > max_quota) {
			return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE)
					.body("El límite de cuota se ha superado (" + quota + ")"); // INSUFFICIENT STORAGE 507 -> String
		}
		
		System.out.printf("Archivo recibido: %s %n", file.getOriginalFilename());
		
		String uuid = UUID.randomUUID().toString();
		
		String filename = uuid + "-" + file.getOriginalFilename();
		
		String path = "D:\\tmp\\" + filename;
		
		System.out.println("El archivo se guardará en la ruta: " + path);
		
		try {
			
			file.transferTo(new File(path));
			
			quota += file.getSize();
			
			return ResponseEntity.ok(filename); // OK 200 -> String
			
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body("No hay acceso al recurso"); // FORBIDDEN 403 -> String
		} catch (Exception e) {
			
			System.out.println("Hubo un error: " + e.getMessage());
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage()); // INTERNAL SERVER ERROR 500 -> String
			
		}
		
	}
	
}
```

> `DownloadApi.java`

```java
package com.nomadacode.s202_download_server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadApi {

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
		
		System.out.println("Descargando el archivo: " + filename);
		
		// TODO: 1. Abrir el archivo con ese nombre
		
		File file = new File("D:\\tmp\\" + filename);
		
		FileInputStream fileInputStream;
		
		try {
			fileInputStream = new FileInputStream(file);
		} catch (Exception e) {
			String error = "No se encuentró el archivo 🤡🤡🤡";
			
			byte[] bytes = error.getBytes();
			
			InputStream inputStream = new ByteArrayInputStream(bytes);
			
			InputStreamResource resource = new InputStreamResource(inputStream);
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("content-disposition", "attachment; filename=error.txt")
					.header("content-type", "application/octet-stream")
					.contentLength(bytes.length)
					.body(resource);
		}
		
		// TODO: 2. Convertir el archivo en recurso
		
		InputStreamResource resource = new InputStreamResource(fileInputStream);
		
		// TODO: 3. Devolver el recurso
		
		String basename = filename.substring(UUID.randomUUID().toString().length() + 1);
		
		return ResponseEntity.ok()
				.header("content-disposition", "attachment; filename=" + basename)
				.header("content-type", "application/octet-stream")
				.contentLength(file.length())
				.body(resource);
		
	}
	
}
```

> `ReporteApi.java`

```java
@PostMapping("/reporte")
public ResponseEntity<Resource> reportar(@RequestParam("file") MultipartFile file) {
    
    Scanner scanner;
    
    try {
        scanner = new Scanner(file.getInputStream());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    
    String html = "";
    
    html += "<ul>";
    
    while (scanner.hasNextLine()) {
        
        String lineNombre = scanner.nextLine();
        
        if (lineNombre.isEmpty() || lineNombre.isBlank()) {
            continue;
        }
        
        String linePrecio = scanner.nextLine();
        String lineCantidad = scanner.nextLine();
        
        Matcher matcherNombre = Pattern.compile("Fruta: (\\w+)").matcher(lineNombre);
        
        String nombre = "SIN NOMBRE";
        
        if (matcherNombre.find()) {
            nombre = matcherNombre.group(1);
        }
        
        Matcher matcherPrecio = Pattern.compile("Precio: \\$(\\d+\\.?\\d*)").matcher(linePrecio);
        
        double precio = 0;
        
        if (matcherPrecio.find()) {
            precio = Double.parseDouble(matcherPrecio.group(1));
        }
        
        Matcher matcherCantidad = Pattern.compile("Cantidad: (\\d+\\.?\\d*)").matcher(lineCantidad);
        
        double cantidad = 0;
        
        if (matcherCantidad.find()) {
            cantidad = Double.parseDouble(matcherCantidad.group(1));
        }
        
        System.out.println(nombre);
        System.out.println(precio);
        System.out.println(cantidad);
        
        html += "<li>";
        html += String.format("<strong style='color:" + (precio > 30 ? "red" : "green") + ";'>%s</strong> "
                + "<em>%.2f</em> "
                + "<button>%.2f</button>", nombre, precio, cantidad);
        html += "</li>";
        
        //System.out.printf("%s [$%.2f MXN] (%.2f KG) %n", nombre, precio, cantidad);
        
    }
    
    html += "</ul>";
    
    System.out.println(html);
    
    byte[] bytes = html.getBytes();
    
    InputStream inputStream = new ByteArrayInputStream(bytes);
    
    InputStreamResource resource = new InputStreamResource(inputStream);
    
    return ResponseEntity.ok()
            .header("content-disposition", "attachment; filename=report.html")
            .header("content-type", "application/octect-stream")
            .contentLength(bytes.length)
            .body(resource);
    
}
```