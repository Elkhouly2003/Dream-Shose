package com.example.dream_shops.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand ;
    private String description;
    private BigDecimal price;
    private int inventory;


    @ManyToOne(cascade =  {CascadeType.DETACH ,CascadeType.REFRESH,
                           CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany( mappedBy = "product", cascade = CascadeType.ALL ,orphanRemoval = true)
    List<Image> images;

    public Product(String name, BigDecimal price, String description, String brand, int inventory, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.brand = brand;
        this.inventory = inventory;
        this.category = category;
    }
}
