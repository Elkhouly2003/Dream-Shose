package com.example.dream_shops.service.cart;

import com.example.dream_shops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
  Cart addCart();
  Cart getCart(Long id);
  void addItemToCart(Long cartId, Long productId, int quantity);
  void clearCart(Long id);
  BigDecimal getCartPrice(Long id);
  void removeItemFromCart(Long cartId, Long itemId);

}
