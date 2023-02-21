# Java Spring Master Class 3

* ¿Qué es Thymeleaf?
* ¿Para qué sirve una plantilla HTML?
* ¿Cómo funciona una plantilla HTML?
* ¿Cómo se pasan valores simples a la plantilla?
* ¿Cómo se pasan valores complejos a la plantilla?
* ¿Cómo se condicionan los elementos mostrados en la plantilla?
* ¿Cómo se repiten elementos en la plantilla?
* ¿Cómo se controla un formulario en la plantilla?
* ¿Qué es React JS?
* ¿Cómo se define un componente en React?
* ¿Cómo se define un hook en React?
* ¿Cómo se hace una petición fetch en React?
* ¿Cómo se consume el API REST en React?

## Práctica 5

> Generar un CRUD de productos en Thymeleaf

- Definir la entidad producto
- Crear un nuevo producto: plantilla del formulario de creación + api crear
- Ver la lista de productos: plantilla de la tabla
- Ver los detalles de un producto: plantilla del producto
- Editar un producto: plantilla del formulario de edición + api editar
- Eliminar un producto: plantilla del formulario de confirmación + api eliminar

## Práctica 6

> Generar un CRUD de productos en React

- Crear un nuevo producto: formulario de creación + hook api crear
- Ver la lista de productos: vista con la tabla de productos
- Ver los detalles de un producto: vista con los detalles del producto
- Editar un producto: formulario de edición + hook api editar
- Eliminar un producto: formulario de confirmación + hook api eliminar

## Códigos importantes

> `DemoController.java`

```java
@GetMapping("/products/{id}")
public String main(@PathVariable Long id, Model model) {
    model.addAttribute("product", productService.getById(id));

    return "product-details-pro";
}
```

> `product-details-pro.html`

```html
<div class="image-container">
    <img src="/static/assets/bottle.jpg" th:src="${product.getPicture()}" />
</div>

<div class="details-labels">
    <p style="color: gray" th:text="${product.getLabelId()}">#123</p>
    <p style="color: hotpink" th:text="${product.getLabelPrice()}">$9.99</p>
</div>

<p style="color: cornflowerblue" th:text="${product.getName()}">Producto de Ejemplo</p>
```

> `App.js`

```js
function useProductStore() {

    const [products, setProducts] = useState([])
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(null)

    return {
        error,
        loading,
        products,
        async getAll() {
            setError(null)
            setLoading(true)

            const url = "http://localhost:8080/api/products"

            try {
                const response = await fetch(url)

                if (response.ok) {
                    const products = await response.json()
                    setProducts(products)
                    setLoading(false)
                } else {
                    const error = await response.text()
                    setError(error)
                }
            } catch(error) {
                setError("El servidor no está disponible")
            }

            setLoading(false)
        },
        async createSampleProduct() {
            setError(null)
            setLoading(true)

            const fruits = ["Manzana", "Kiwi", "Limón", "Piña"]
            const sizes = ["Nothing", "Some", "Many", "Too many", "Close to nothing"]
            const name = fruits[Math.floor(Math.random() * fruits.length)] + " " + sizes[Math.floor(Math.random() * sizes.length)]
            const price = Math.random() * 1000 + 20
            
            try {
                const response = await fetch("http://localhost:8080/api/products/add", {
                    method: "put",
                    headers: {
                        "content-type": "application/json"
                    },
                    body: JSON.stringify({
                        name,
                        price
                    })
                })

                if (response.ok) {
                    this.getAll()
                } else {
                    const error = await response.text()
                    setError(error)
                }
            } catch (error) {
                
            }

            setLoading(false)
        }
    }

}
```

> `App.js`

```js
export default function App() {

    const store = useProductStore()

    useEffect(() => {
        store.getAll()
    }, [])

    return (
        <div>
            <h1>Hola React JS </h1>

            <button onClick={e => {
                store.createSampleProduct()
            }}>Agregar producto de ejemplo</button>

            {/* <ProductDetails id={123} name="Pepsi" price={12.45} />
            <ProductDetails id={456} name="Galletas Marías" price={32.45} />
            <ProductDetails id={789} name="Chocoroll" price={67.45} /> */}

            {
                store.error && (
                    <p style={{ backgroundColor: "red", color: "white", padding: 10 }}>{store.error}</p>
                )
            }

            {
                store.products.length == 0 && (
                    <p>No hay productos</p>
                )
            }

            {
                store.products.map(product => (
                    <ProductDetails 
                        key={product.id}
                        id={product.id}
                        name={product.name}
                        price={product.price}
                        picture={product.picture}
                    />
                ))
            }

        </div>
    )

}
```
