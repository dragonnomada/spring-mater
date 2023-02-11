package com.example.r3_repaso_general.repositories;

import com.example.r3_repaso_general.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {

    Optional<Product> finById(Long id);

    List<Product> findAll();

    Optional<Product> create(String name, Double price);

    Optional<Product> update(Long id, Optional<String> name, Optional<Double> price);

    Optional<Product> update(Product product, Optional<String> name, Optional<Double> price);

    Optional<Product> delete(Long id);

    Optional<Product> delete(Product product);

}
