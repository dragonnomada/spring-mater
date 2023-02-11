package com.example.r3_repaso_general;

import com.example.r3_repaso_general.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//@RestController
public class SampleApi {

    @GetMapping("/api/sample")
    public String sample() {
        return "Hi there";
    }

    @GetMapping("/api/input")
    public ResponseEntity<String> input(@RequestParam("name") String name, @RequestParam("age") int age) {

        if (name.isBlank() || name.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid name");
        }

        String message = String.format("Hi %s, your age is %d years old", name, age);

        return ResponseEntity.ok(message);

    }

    @GetMapping("/api/product")
    public Product getProduct() {

        Product product = new Product(1L, "Sample Product", 123.456);

        //product.setId(1L);
        //product.setName("Sample product");
        //product.setPrice(123.456);

        return product;

    }

    @GetMapping("/api/products")
    public List<Product> getAllProduct() {

        List<Product> products = new ArrayList<>();

        // TODO: Crear producto
        products.add(
                Product.builder()
                        .id(1L)
                        .name("Sample Product 1")
                        .price(123.456)
                        .build()
        );
        products.add(
                Product.builder()
                        .id(2L)
                        .name("Sample Product 2")
                        .price(67.89)
                        .build()
        );
        products.add(
                Product.builder()
                        .id(3L)
                        .name("Sample Product 3")
                        .price(56.21)
                        .build()
        );

        return products;

    }

}

// ⛔ SampleApi api = new SampleApi()
// 🆗 @Controller | @RestController | @Transactional | @Service | @Repository | @Autowired
