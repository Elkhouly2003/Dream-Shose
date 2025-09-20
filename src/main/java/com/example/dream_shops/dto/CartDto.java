package com.example.dream_shops.dto;

import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Long id ;
    private BigDecimal totalPrice ;
    private Set<CartItemDto> itemsDto = new HashSet<>();

    public static CartDto cartToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalPrice(cart.getTotalPrice());
        Set<CartItemDto> cartItemDtos = new HashSet<>();

         for(CartItem cartItem : cart.getItems()) {
             CartItemDto cartItemDto = new CartItemDto();
             cartItemDto = CartItemDto.CartItemToCartItemDto(cartItem);
             cartItemDtos.add(cartItemDto);
         }
         cartDto.setItemsDto(cartItemDtos);

         return cartDto;
    }
}
