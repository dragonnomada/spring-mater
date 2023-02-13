// import logo from './logo.svg';
// import './App.css';

import { useEffect, useState } from "react";
import ProductDetails from "./ProductDetails";

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//         <p>
//           Edit <code>src/App.js</code> and save to reload.
//         </p>
//         <a
//           className="App-link"
//           href="https://reactjs.org"
//           target="_blank"
//           rel="noopener noreferrer"
//         >
//           Learn React
//         </a>
//       </header>
//     </div>
//   );
// }

// export default App;

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
