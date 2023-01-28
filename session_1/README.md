# Java Spring Master Class 1

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
* ¿Qué es @PathVariable("<name>")? Es recuperar un valor variable desde la url en una posición dada
* ¿Qué es @PutMapping? Mapeo tipo PUT de una ruta a un valor (simple o complejo)
* ¿Qué es @DeleteMapping? Mapeo tipoDELETE de una ruta a un valor (simple o complejo)
* ¿Qué es una Entidad?  Una clase que representa un modelo de datos
* ¿Qué es @RequestBody? La forma de recuperar los datos de una entidad en el API
* ¿Qué es un Servicio? Una clase que define las operaciones y transacciones lógicas

## Práctica 1

> Diseñar un contador de peticiones

- Crear un @RestController
- Agregar un @GetMapping("/contador")
- Agregar un @PostMapping("/contador/incrementar")
- Agregar un @PostMapping("/contador/decrementar")
- Agregar un @DeleteMapping("/contador/reiniciar")
- Agregar un @PutMapping("/contador/ajustar/{valor}")

## Práctica 2

> Diseñar una tienda de frutas

- Crear un @RestController
- Crear la entidad FrutaEntity(id, nombre, cantidad)
- Crear el Servicio TiendaFrutasService(frutas)
- Agregar las transacciones para consultar, agregar, modificar y eliminar frutas y cantidades
- Agregar un @GetMapping("/frutas")
- Agregar un @GetMapping("/frutas/{id}")
- Agregar un @PutMapping("/frutas/crear")
- Agregar un @PostMapping("/frutas/{id}/actualizar/nombre")
- Agregar un @PostMapping("/frutas/{id}/actualizar/cantidad")
- Agregar un @PutMapping("/frutas/{id}/agregar/cantidad")
- Agregar un @PutMapping("/frutas/{id}/quitar/cantidad")
- Agregar un @DeleteMapping("/frutas/{id}/eliminar")
- Agregar un @GetMapping("/frutas/reporte")
