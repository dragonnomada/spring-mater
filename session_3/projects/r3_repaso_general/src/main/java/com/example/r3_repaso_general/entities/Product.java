package com.example.r3_repaso_general.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private Long id;
    private String name;
    private Double price;

}
