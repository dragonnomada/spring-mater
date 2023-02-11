package com.example.r3_repaso_general.controllers;

import com.example.r3_repaso_general.entities.Product;
import com.example.r3_repaso_general.repositories.InMemoryProductRepository;
import com.example.r3_repaso_general.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductApi {

    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    /*@Autowired
    private ProductService productService;*/

    @GetMapping("/api/products")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("api/products/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.ok(productService.create(product.getName(), product.getPrice()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("api/products/{id}/update/name")
    public ResponseEntity<Product> updateNameProduct(@PathVariable Long id, @RequestParam String name) {
        try {
            return ResponseEntity.ok(productService.modifyName(id, name));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("api/products/{id}/update/price")
    public ResponseEntity<Product> updatePriceProduct(@PathVariable Long id, @RequestParam Double price) {
        try {
            return ResponseEntity.ok(productService.modifyPrice(id, price));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("api/products/{id}/delete")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
