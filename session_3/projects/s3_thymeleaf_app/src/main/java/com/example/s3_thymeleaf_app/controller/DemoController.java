package com.example.s3_thymeleaf_app.controller;

import com.example.s3_thymeleaf_app.dto.ProductEntity;
import com.example.s3_thymeleaf_app.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(maxAge = 3600)
@Controller
public class DemoController {

    private final ProductService productService;

    public DemoController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("products", productService.getAll());

        return "products-all";
    }

    @GetMapping("/products/{id}")
    public String main(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getById(id));

        return "product-details-pro";
    }

    @GetMapping("/home")
    public String home() {
        return "index.html";
    }

    @GetMapping("/product/demo")
    public String productDemo(Model model) {

        Random random = new Random();

        model.addAttribute("id", 456);
        model.addAttribute("name", "Coca Cola");
        model.addAttribute("price", random.nextDouble() * 100 + 20);
        model.addAttribute("picture", "https://th.bing.com/th/id/R.ad8a3117fc136ec075fb462c09528e31?rik=ZxYL0nCjva9t7Q&pid=ImgRaw&r=0");

        return "product-details";
    }

    private ProductEntity createSampleProduct() {
        Random random = new Random();

        long id = Math.abs(random.nextLong() % 1000);

        String[] frutas = new String[] {
                "Papaya", "Limón", "Kiwi", "Naranja", "Piña"
        };

        String[] tamaños = new String[] {
                "Chica", "Mediana", "Grande", "Venti", "Alto"
        };

        String name = frutas[(int)(id % frutas.length)] + " " + tamaños[(int)(id % tamaños.length)];

        return ProductEntity.builder()
                .id(id)
                .name(name)
                .price(random.nextDouble() * 1000 + 20)
                .picture(String.format("https://picsum.photos/id/" + id + "/400"))
                .build();
    }

    @GetMapping("/products/all/sample")
    public String productAllSample(Model model) throws InterruptedException {
        List<ProductEntity> products = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            products.add(createSampleProduct());
        }

        model.addAttribute("products", products);

        return "products-all";
    }

    @GetMapping("/product/sample")
    public String productSample(Model model) {

        model.addAttribute("product", createSampleProduct());

        return "product-details-pro";
    }

}
