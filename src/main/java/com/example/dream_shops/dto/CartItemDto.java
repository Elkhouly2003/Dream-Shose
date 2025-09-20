package com.example.dream_shops.dto;

import com.example.dream_shops.dto.response.ProductInfoDto;
import com.example.dream_shops.model.CartItem;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductInfoDto productDto;

    public static CartItemDto CartItemToCartItemDto(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setQuantity(cartItem.getQuantity());
        cartItemDto.setUnitPrice(cartItem.getUnitPrice());
        cartItemDto.setTotalPrice(cartItem.getCartItemPrice());
        cartItemDto.productDto = ProductInfoDto.productToProductInfoDto(cartItem.getProduct());

        return cartItemDto;
    }
}
