package com.example.dream_shops.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand ;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String category;

}
