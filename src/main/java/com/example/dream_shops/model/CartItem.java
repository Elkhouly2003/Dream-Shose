package com.example.dream_shops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal CartItemPrice;


    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE
            , CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id")
    private Cart cart;


    public BigDecimal getCartItemPrice() {
        return new BigDecimal(quantity).multiply(unitPrice);
    }
}
