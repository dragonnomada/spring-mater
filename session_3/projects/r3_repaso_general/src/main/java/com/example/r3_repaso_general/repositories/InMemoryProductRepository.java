package com.example.r3_repaso_general.repositories;

import com.example.r3_repaso_general.entities.Product;
import org.springframework.stereotype.Component;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private List<Product> products = new ArrayList<>();

    @Override
    public Optional<Product> finById(Long id) {
        System.out.printf("Buscando producto: %d %n", id);

        return products.stream().filter(product -> product.getId() == id).findFirst();
    }

    @Override
    public List<Product> findAll() {
        return products.stream().map(product ->
                Product.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .build()
        ).toList();
    }

    @Override
    public Optional<Product> create(String name, Double price) {
        Optional<Long> maxId = products.stream()
                .map(product -> product.getId())
                //.sorted((aId, bId) -> bId.compareTo(aId))
                .sorted(Comparator.reverseOrder())
                .findFirst();

        Product product =  Product.builder()
                .id(maxId.isPresent() ? maxId.get() + 1 : 1)
                .name(name)
                .price(price)
                .build();

        products.add(product);

        return Optional.of(product);
    }

    @Override
    public Optional<Product> update(Long id, Optional<String> name, Optional<Double> price) {
        if (name.isEmpty() && price.isEmpty()) {
            return Optional.empty();
        }

        Optional<Product> productFound = finById(id);

        if (productFound.isEmpty()) {
            return Optional.empty();
        }

        return update(productFound.get(), name, price);
    }

    @Override
    public Optional<Product> update(Product product, Optional<String> name, Optional<Double> price) {

        if (name.isEmpty() && price.isEmpty()) {
            return Optional.empty();
        }

        Optional<Product> productFound = products.stream()
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
    public Optional<Product> delete(Long id) {
        Optional<Product> productFound = finById(id);

        if (productFound.isEmpty()) {
            return Optional.empty();
        }

        System.out.printf("Eliminando al producto %d %n", id);

        products.remove(productFound.get());

        return productFound;
    }

    @Override
    public Optional<Product> delete(Product product) {
        return delete(product.getId());
    }
}
