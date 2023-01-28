# 202 - Descarga de archivos del servidor

Por [Alan Badillo Salas](alan@nomadacode.com)

## Introducción

Al crear una clase anotada como `@RestController` podemos definir funciones anotadas como `@GetMapping` o `@PostMapping` para controlar las peticiones *REST* que llegan al servidor.

Dentro de la ruta podemos extraer partes variables que se escriban en la ruta para usarlos como variables al procesar la petición. Esto nos permitirá que una misma ruta controle todas las peticiones con la misma estructura de ruta, pero contenido variable. Para lograrlo definimos en la ruta la parte variable y colocamos algún nombre dentro del `@RequestMapping`, por ejemplo, `@GetMapping("/download/{filename}")`, donde esperamos que después de `/download/` lo siguiente represente el nombre del archivo.  

Usamos la anotación `@PathVariable(<variable>)`, donde `<variable>` es el nombre asignado a la parte variable sobre la ruta. Y así podremos recuperar el contenido variable en tipo `String`, `int`, `double`, etc.

De esa manera podríamos recuperar el nombre del archivo que quieren descargar, abrir el archivo como un recurso de tipo `InputStreamResource` usando `FileInputStream` sobre el archivo.

Además podemos responder un `ResponseEntity<Resource>` con el recurso que queremos enviar en modo `MediaType.APPLICATION_OCTET_STREAM`.

Para ello ajustamos las cabeceras de la respuesta mediante `HttpHeaders` y le indicamos que se enviará un archivo adjunto `attachment; filename=<name>` con el nombre `<name>`.

Si todo sale bien responderemos bajo el estatus OK (200), con las cabeceras, el tamaño del archivo, el tipo de archivo y el archivo como recurso de respuesta.

Si algo sale mal, simularemos un recurso como un archivo de texto con el texto del error llamado `error.txt` para que lo descargue como si fuera el recurso descargado.

## Código de Estudio

Estudia el siguiente código para comprender cómo podemos crear una ruta de tipo `@GetMapping` que reciba el nombre del archivo que se desea descargar.

```java
// Define una función pública `download` que recupere la parte variable en la ruta
// que representa el nombre del archivo mediante `@PathVariable("<name>")` de
// tipo `String` y que será el nombre del archivo que se desea descargar.
@GetMapping("/download/{filename}")
public ResponseEntity<Resource> download(@PathVariable("filename") String filename) {
    
    // Define la ruta del archivo
    String path = String.format("D:\\tmp\\%s", filename);
    
    // Recupera el nombre del archivo quitando el `UUID`
    String basename = filename.substring(UUID.randomUUID().toString().length() + 1);
    
    // Convierte el nombre del archivo en formato de URL
    basename = URLEncoder.encode(basename, StandardCharsets.UTF_8);
    
    System.out.println(path);
    
    // Intenta abrir el archivo para descargarlo
    try {
        
        // Abre el archivo en esa ruta
        File file = new File(path);
        
        // Transforma el archivo en un recurso
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        
        // Configura las cabeceras para descargar el archivo
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", basename));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
        // Devuelve el recurso (el archivo transformado a recurso)
        // indicando las cabeceras, el tamaño del archivo, el tipo de contenido
        // y el recurso mismo
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
                
    } catch (Exception e) {
        
        // Transforma el mensaje de error en un arreglo de bytes
        InputStream input = new ByteArrayInputStream(e.getMessage().getBytes());
        
        // Transforma el flujo de bytes (el mensaje de error)
        // en un recurso para descargarlo
        InputStreamResource resource = new InputStreamResource(input);
        
        // Define que el recurso será `error.txt` mediante las cabeceras
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=error.txt");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        
        // Devuelve el recurso bajo el estatus 500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers)
                .contentLength(e.getMessage().getBytes().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
        
    }
        
}
```

