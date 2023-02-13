package com.example.s3_thymeleaf_app.controller;

import com.example.s3_thymeleaf_app.dto.ProductEntity;
import com.example.s3_thymeleaf_app.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class ProductApi {

    private final ProductService productService;

    public ProductApi(ProductService productService) {
        this.productService = productService;
    }

    /*@Autowired
    private ProductService productService;*/

    @GetMapping("/api/products")
    public List<ProductEntity> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(productService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("api/products/add")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity product) {
        try {
            return ResponseEntity.ok(productService.create(product.getName(), product.getPrice()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("api/products/{id}/update/name")
    public ResponseEntity<ProductEntity> updateNameProduct(@PathVariable Long id, @RequestParam String name) {
        try {
            return ResponseEntity.ok(productService.modifyName(id, name));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("api/products/{id}/update/price")
    public ResponseEntity<ProductEntity> updatePriceProduct(@PathVariable Long id, @RequestParam Double price) {
        try {
            return ResponseEntity.ok(productService.modifyPrice(id, price));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("api/products/{id}/delete")
    public ResponseEntity<ProductEntity> deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
