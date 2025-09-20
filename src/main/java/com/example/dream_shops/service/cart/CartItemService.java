package com.example.dream_shops.service.cart;


import com.example.dream_shops.model.Cart;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;


    @Override
    public void updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems().stream().filter(item -> item.getId().equals(itemId))
                                 .findFirst().ifPresent(item -> {item.setQuantity(quantity);
                                  cartItemRepository.save(item);
                                 });

    }
}
