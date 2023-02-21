# Java Spring Master Class 2

* ¿Para qué sirve `ResponseEntity<T>`?
* ¿Para qué sirve `@RequestParam`?
* ¿Cómo funciona `MultipartFile`?
* ¿Cómo devolver un estatus Http con `ResponseEntity.status(HttpStatus.<STATUS>)`?
* ¿Cómo transferir el archivo subido con `file.transferTo(new File(path))`?
* ¿Cómo devolver un estatus exitóso con ` ResponseEntity.ok(<body>)`?
* ¿Para qué sirve `ResponseEntity<Resource>`?
* ¿Cómo funciona `Resource`?
* ¿Cómo crear un `Resource` con `Resource <- InputStreamResource <- InputStream <- ByteArrayInputStream <- byte[]`? 
* ¿Para qué sirve el *header* `.header("content-disposition", "attachment; filename=<file name>")` de `ResponseEntity`?
* ¿Para qué sirve el *header* `.header("content-type", "application/octet-stream")` de `ResponseEntity`?
* ¿Para qué sirve el *contentLength* `.contentLength(<file length>)` de `ResponseEntity`?
* ¿Para qué sirve el *body* `..body(<resource>)` de `ResponseEntity`?
* ¿Para qué sirve `@Entity` de *JPA*?
* ¿Para qué sirve `@Table(name = "<table name>")` de *JPA*?
* ¿Para qué sirve `@Id` de *JPA*?
* ¿Para qué sirve `@GeneratedValue(strategy = GenerationType.AUTO)` de *JPA*?
* ¿Para qué sirve `@Column(name = "<column name>")` de *JPA*?
* ¿Para qué sirve crear la interfaz `public interface [Entity Repository] extends CrudRepository<[Entity], [Type Id]>` de *JPA*?
* ¿Cómo se define un *query* nativo en el repositorio *crud* como `@Query(value = "<query con :params>", nativeQuery = true)`?
* ¿Para qué sirve `@Autowired`?
* ¿Para qué sirve `@RequestBody`?

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

DOCS: 

* [Configurar un proyecto con Spring Boot y Hibernate](https://medium.com/full-stack-java/configurar-un-proyecto-con-spring-boot-y-hibernate-5893125888cf)
* [Modelar un Negocio en Spring Boot y Hibernate — Parte I](https://medium.com/full-stack-java/modelar-un-negocio-en-spring-boot-y-hibernate-parte-i-a8978d72e413)
* [Modelar un Negocio en Spring Boot y Hibernate — Parte II](https://medium.com/full-stack-java/modelar-un-negocio-en-spring-boot-y-hibernate-parte-ii-67c59640cd07)
* [Modelar un Negocio en Spring Boot y Hibernate — Parte III](https://medium.com/full-stack-java/modelar-un-negocio-en-spring-boot-y-hibernate-parte-iii-109b298a7d73)

> `application.properties`

```text
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_frutas
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

spring.datasource.username=root
spring.datasource.password=password

spring.datasource.dbcp2.test-while-idle=true
spring.r2dbc.pool.validation-query=SELECT NOW()
spring.jpa.hibernate.ddl-auto=update

server.port=8181
```

> `Fruta.java`

```java
package com.nomadacode.s204_hibernate_mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// CLASE POJO (Plain Old Java Object)
// DTO (Data Transactional Object)

@Entity
@Table(name = "frutas")
public class Fruta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;
	
	@Column(name = "name")
	String nombre;
	
	@Column(name = "price")
	double precio;
	
	@Column(name = "quantity")
	double cantidad;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	
}

```

> `FrutaRepository.java`

```java
package com.nomadacode.s204_hibernate_mysql;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

@Transactional
public interface FrutaRepository extends CrudRepository<Fruta, Integer> {

	// Crear queries personalizados
	@Query(value = "SELECT * FROM frutas WHERE price >= :minPrice", nativeQuery = true)
	public Iterable<Fruta> getByPrecioGreatherThan(@Param("minPrice") double minPrice);
	
}
```

> `FrutaService.java`

```java
package com.nomadacode.s204_hibernate_mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FrutaService {

	@Autowired
	FrutaRepository frutaRepository;
	
}
```

> `FrutaApi.java`

```java
package com.nomadacode.s204_hibernate_mysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrutaApi {

	@Autowired
	FrutaService frutaService;
	
	@GetMapping("/api/frutas")
	public Iterable<Fruta> getAllFrutas() {
		
		return frutaService.frutaRepository.findAll();
		
	}
	
	@PutMapping("/api/frutas/nueva")
	public Fruta createFruta(@RequestBody Fruta fruta) {
		
		return frutaService.frutaRepository.save(fruta);
		
	}
	
	@GetMapping("/api/frutas/{id}")
	public Optional<Fruta> getFrutaById(@PathVariable("id") int id) {
		
		return frutaService.frutaRepository.findById(id);
		
	}
	
	@GetMapping("/api/frutas/precio")
	public Iterable<Fruta> getFrutaByIdWithPrecio(
			@RequestParam(name = "min", defaultValue = "0") double minPrecio
	) {
		
		return frutaService.frutaRepository.getByPrecioGreatherThan(minPrecio);
		
	}
	
}
```