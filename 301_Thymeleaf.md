# 202 - Descarga de archivos del servidor

Por [Alan Badillo Salas](mailto:alan@nomadacode.com)

## Introducción

**Thymeleaf** es un motor de plantillas que puede ser agregado en el *Spring Starter* y tiene como objetivo simplificar el paso de variables y mensajes entre el controlador y las vistas html (las plantillas).

## Usar una plantilla simple

Al crear una clase anotada como `@Controller` podemos devolver un `String` con el nombre de la plantilla que se desea utilizar ubicada en `resources/templates`.

> Ejemplo de un *Controlador* que usa el motor de plantillas de **Thymeleaf**

```java
@Controller
public class HelloApi {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
```

La ruta `/hello` hará referencia a la plantilla `/resources/templates/hello.html`.

## Enviar variables a las plantillas

Podemos enviar variables a las plantillas mediante el parámetro `Model` inyectado en la petición. Usando `model.addAttribute(<name>, <value>)` pasaremos el valor que podrá ser consumido en la plantilla.

> Ejemplo del envío de una variable tipo `String` a la plantilla

```java
@GetMapping("/demo")
public String demo(Model model) {
    model.addAttribute("title", "Welcome to Demo");
    return "demo";
}
```

La ruta `/demo` hará referencia a la plantilla `/resources/templates/demo.html` y expondrá en el contexto la variable `title` con el valor `"Welcome to Demo"` de tipo `String`.

## Consumo de variables 

Dentro de una plantilla `.html` podremos acceder a las variables y sustituir los atributos de las etiquetas por estos valores.

> Ejemplo del reemplazo del texto de una etiqueta `<h1>` por el valor de la variable expuesta `title`

```html
<h1 th:text="${title}">Texto por defecto</h1>
```

La plantilla ahora mostrará el valor de la variable `title` expuesta sobre el `model.addAttribute("title", "Welcome to Demo")`.

## Paso de variables entidades

Así como pasamos un valor `String` sobre la variable (el atributo del modelo). Podemos también pasar objetos más complejos. Por ejemplo, si tenemos una clase *entidad* y queremos exponer una instancia sobre la plantilla.

> Ejemplo de la clase *entidad* `Product`

```java
public class Product {

    private String name;
    private Double price;

    public Producto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

}
```

Una vez definida la entidad, podremos crear instancias de esa entidad con distintos valores, por ejemplo: `Product myProduct = new Product("Coca Cola", 17.56)`

> Ejemplo del envío de una instancia de la *entidad* `Product` a la plantilla

```java
@GetMapping("/product")
public String getProduct(Model model) {
    Product product = new Product("Producto de Ejemplo", 123.45);

    model.addAttribute("product", product);
    
    return "product";
}
```

La ruta `/product` hará referencia a la plantilla `/resources/templates/product.html` y expondrá en el contexto la variable `product` con el valor como la instancia de la clase *entidad* `Product`.

> Ejemplo del consumo de la variable (con la instancia compleja de `Product`) en la plantilla `/resources/templates/product.html`

```html
<h1>Product Details</h1>

<p>
    <span>Name:</span>
    <span th:text="${product.getName()}">Nombre por defecto</span>
</p>
<p>
    <span>Price:</span>
    <span th:text="${product.getPrice()}">Precio por defecto</span>
</p>
```

La plantilla ahora mostrará los valores extraídos desde la instancia.