# 201 - Subida de archivos al servidor

Por [Alan Badillo Salas](mailto:alan@nomadacode.com)

## Introducción

Al crear una clase anotada como `@RestController` podemos definir funciones anotadas como `@GetMapping` o `@PostMapping` para controlar las peticiones *REST* que llegan al servidor.

Dentro de los parámetros de configuración podemos extraer los parámetros de la petición mediante la anotación `@RequestParam` en los argumentos. Esto nos permitirá extraer un valor desde la petición y poder recuperarlo en código.

Si queremos que ese parámetro en particular se comporte como un archivo, podemos usar la clase `MultipartFile` para recuperar el archivo que está siendo enviado al servidor (generalmente en el parámetro `file`).

Al recibir el archivo podemos recuperar su nombre original mediante `file.getOriginalFilename()` y determinar en qué lugar será guardado dentro del servidor.

Así, podremos transferir el archivo enviado al servidor mediante `file.transferTo(new File(<path>))` donde el `<path>` será la ruta completa al destino del archivo cargado.

Mediante un `UUID.randomUUID().toString()` podremos generar un nombre aleatorio único para evitar que se reemplacen archivos subidos al servidor con el mismo nombre.

Finalmente, podremos responder al cliente mediante un `ResponseEntity<T>` donde `T` será el tipo de dato devuelto en la respuesta. Este nos permitirá establecer un estatus de respuesta, cabeceras y demás. Por ejemplo, un `ResponseEntity<String>` devolverá un `String` bajo algún estatus *HTTP* por si necesitaramos avisar que hubo algún error.

## Código de Estudio

Estudia el siguiente código para comprender cómo podemos crear una ruta de tipo `@PostMapping` que reciba los archivos que se desean subir al servidor.

```java
// Define la función pública `upload` anotada como `@PostMapping`
// para la ruta `/upload` que recibe el parámetro `file` desde la petición
// en el campo `file` anotada como `@RequestParam("file")` que recibe
// un `MultipartFile` que es el archivo subido
@PostMapping("/upload")
public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    
    // Crea un `String` con caracteres únicos aleatorios en formato UUID
    // el cuál es del estilo: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
    String uuid = UUID.randomUUID().toString();
    
    // Crea el nombre del archivo con el que será almacenado
    // uniendo el identificador único y nombre original del archivo
    String filename = uuid + "." + file.getOriginalFilename();
    
    // Crea la ruta del archivo en la carpeta D:\tmp usando el nombre único
    String path = String.format("D:\\tmp\\%s", filename);
    
    // Intenta transferir el archivo a la ruta donde será guardado
    try {
        
        // Transfiere el archivo a la ruta donde será guardado 
        file.transferTo(new File(path));
        
        // Responde bajo el estatus OK (200) el nombre del archivo,
        // si el cliente pierde este nombre no podrá recuperarlo
        return ResponseEntity.ok(filename);
                
    } catch (Exception e) {
        
        // Si hay un error al transferir el archivo responde
        // bajo el estatus 500 el mensaje con el error
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
                
    }
    
}
```

