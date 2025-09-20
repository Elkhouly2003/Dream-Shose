package com.example.dream_shops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalPrice ;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL , orphanRemoval = true ,fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
