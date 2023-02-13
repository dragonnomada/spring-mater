package com.example.s3_thymeleaf_app.repositories;

import com.example.s3_thymeleaf_app.dto.ProductEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {

    Optional<ProductEntity> finById(Long id);

    List<ProductEntity> findAll();

    Optional<ProductEntity> create(String name, Double price);

    Optional<ProductEntity> update(Long id, Optional<String> name, Optional<Double> price);

    Optional<ProductEntity> update(ProductEntity product, Optional<String> name, Optional<Double> price);

    Optional<ProductEntity> delete(Long id);

    Optional<ProductEntity> delete(ProductEntity product);

}
