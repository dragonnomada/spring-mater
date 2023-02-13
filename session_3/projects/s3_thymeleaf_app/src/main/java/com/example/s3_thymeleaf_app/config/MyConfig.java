package com.example.s3_thymeleaf_app.config;

import com.example.s3_thymeleaf_app.repositories.InMemoryProductRepository;
import com.example.s3_thymeleaf_app.repositories.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }

}
