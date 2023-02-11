package com.example.r3_repaso_general.config;

import com.example.r3_repaso_general.repositories.InMemoryProductRepository;
import com.example.r3_repaso_general.repositories.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

}
