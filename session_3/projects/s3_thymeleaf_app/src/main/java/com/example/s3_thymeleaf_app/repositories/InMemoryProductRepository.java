package com.example.s3_thymeleaf_app.repositories;

import com.example.s3_thymeleaf_app.dto.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private List<ProductEntity> products = new ArrayList<>();

    @Override
    public Optional<ProductEntity> finById(Long id) {
        System.out.printf("Buscando producto: %d %n", id);

        return products.stream()
                .filter(product -> product.getId() == id)
                .map(product ->
                        ProductEntity.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .picture(product.getPicture())
                                .build()
                )
                .findFirst();
    }

    @Override
    public List<ProductEntity> findAll() {
        return products.stream().map(product ->
                ProductEntity.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .picture(product.getPicture())
                        .build()
        ).toList();
    }

    @Override
    public Optional<ProductEntity> create(String name, Double price) {
        Optional<Long> maxId = products.stream()
                .map(product -> product.getId())
                //.sorted((aId, bId) -> bId.compareTo(aId))
                .sorted(Comparator.reverseOrder())
                .findFirst();

        long id = maxId.isPresent() ? maxId.get() + 1 : 1;

        ProductEntity product =  ProductEntity.builder()
                .id(id)
                .name(name)
                .price(price)
                .picture("https://picsum.photos/id/" + id + "/400")
                .build();

        products.add(product);

        return Optional.of(product);
    }

    @Override
    public Optional<ProductEntity> update(Long id, Optional<String> name, Optional<Double> price) {
        if (name.isEmpty() && price.isEmpty()) {
            return Optional.empty();
        }

        Optional<ProductEntity> productFound = finById(id);

        if (productFound.isEmpty()) {
            return Optional.empty();
        }

        return update(productFound.get(), name, price);
    }

    @Override
    public Optional<ProductEntity> update(ProductEntity product, Optional<String> name, Optional<Double> price) {

        if (name.isEmpty() && price.isEmpty()) {
            return Optional.empty();
        }

        Optional<ProductEntity> productFound = products.stream()
                .filter(productOriginal -> productOriginal.equals(product))
                .findFirst();

        if (productFound.isEmpty()) {
            return Optional.empty();
        }

        if (name.isPresent()) {
            productFound.get().setName(name.get());
        }

        if (price.isPresent()) {
            productFound.get().setPrice(price.get());
        }

        return productFound;
    }

    @Override
    public Optional<ProductEntity> delete(Long id) {
        Optional<ProductEntity> productFound = finById(id);

        if (productFound.isEmpty()) {
            return Optional.empty();
        }

        System.out.printf("Eliminando al producto %d %n", id);

        products.remove(productFound.get());

        return productFound;
    }

    @Override
    public Optional<ProductEntity> delete(ProductEntity product) {
        return delete(product.getId());
    }
}
