package com.example.r3_repaso_general.services;

import com.example.r3_repaso_general.entities.Product;
import com.example.r3_repaso_general.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.finById(id).orElseThrow();
    }

    private void verifyName(String name) throws Exception {
        if (name.isEmpty() || name.isBlank()) {
            throw new Exception("Invalid name");
        }
    }

    private void verifyPrice(Double price) throws Exception {
        if (price.isInfinite() || price.isNaN()) {
            throw new Exception("Invalid price");
        }

        if (price <= 0) {
            throw new Exception("Negative price");
        }
    }

    private void verifyPriceIsOneCent(Double price) throws Exception {
        if (price != 0.01) {
            throw new Exception("Price must be 0.01");
        }
    }

    public Product create(String name, Double price) throws Exception {

        verifyName(name);
        verifyPrice(price);

        return productRepository.create(name, price).orElseThrow();
    }

    public Product modifyName(Long id, String name) throws Exception {
        verifyName(name);

        return productRepository.update(id, Optional.of(name), Optional.empty()).orElseThrow();
    }

    public Product modifyPrice(Long id, Double price) throws Exception {
        verifyPrice(price);

        return productRepository.update(id, Optional.empty(), Optional.of(price)).orElseThrow();
    }

    public Product delete(Long id) throws Exception {
        // TODO: Verificar que el producto no tenga existencias en la base de datos
        Product product = getById(id);

        verifyPriceIsOneCent(product.getPrice());

        return productRepository.delete(id).orElseThrow();
    }

}
