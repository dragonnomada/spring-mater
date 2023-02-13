package com.example.s3_thymeleaf_app.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEntity {

    private Long id;
    private String name;
    private Double price;
    private String picture;

    public String getLabelId() {
        return "#" + id;
    }

    public String getLabelPrice() {
        double fixedPrice = Math.rint(price * 100) / 100;
        return "$" + fixedPrice;
    }

}
