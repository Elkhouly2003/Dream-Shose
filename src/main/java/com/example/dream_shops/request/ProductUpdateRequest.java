package com.example.dream_shops.request;

import com.example.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private String name;
    private String brand ;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;

}
