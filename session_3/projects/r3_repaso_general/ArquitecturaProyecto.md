# Arquitectura del Proyecto

## Introducción

Existen diferentes capas en el negocio para 
separar las distas piezas de código y
simplificar el mantenimiento a la larga.

## DTO - Capa de Objeto de Datos Transaccionales

El **DTO** (*Data Transactional Object*) modela
objetos que serán usados en las transacciones,
por ejemplo, entidades que son usadas en 
la recepción de datos (entradas al sistema)
o en la respuesta de datos (salidas del sistema).

> Ejemplo de un Objeto de Datos Transaccionales

```java
public class User {
    
    private Long id;
    private String username;
    private String email;
    private String password;
    
}
```

Generalmente se usa el sufijo `Entity` o `DTO` y
se disponen en el paquete `.dto`, `.entity` o `entites`.

También es posible ver en este nivel a
los repositorios o almacenes de entidades.

## DAL - Capa de Acceso a los Datos

La **DAL**  (*Data Access Layer*) se compone
principalmente de *Repositorios* y *Servicios*.

Los repositorios están orientados a la persistencia
de las entidades, por ejemplo, en operaciones 
*CRUD* (*Create, Read, Update, Delete* o *Crea, Lee, Actualiza, Elimina*)
y se limitan a reportar si salió bien o mal la transacción
requerida, pero no aumentan lógica adicional al negocio.

Por otro lado, los servicios están orientados a
las operaciones entre las entidades y sus transacciones,
por ejemplo, a utilizar de forma correcta los
repositorios, tanto en orden, como agregando
lógica de negocio, para garantizar un buen
uso de las transacciones definidas en el sistema.

> Ejemplo de un Repositorio de Pagos

```java
public interface IPayable {
    
    Optional<Ticket> pay(List<Product> products);

    Optional<Ticket> cancel(Ticket ticket);
    
}
```

> Ejemplo de un servicio de Pagos

```java
import com.example.r3_repaso_general.entities.Product;

public class PayService {

    IPayable payable;

    // TODO: Constructor que inyecte el payable

    public Ticket pay(ShoppingCart cart) {

        List<Product> products = cart.getProduct();

        // TODO: Verificar que el almacén no esté bloqueado
        // TODO: Espera a que el almacén se desbloquee
        // TODO: Bloquear el almacén
        // TODO: Verificar existencias para cada producto
        // TODO: Notificar baja de existencia para cada producto
        
        Ticket ticket = payable.pay(products);
        
        // TODO: Preparar envio del carrito
        
    }

}
```
