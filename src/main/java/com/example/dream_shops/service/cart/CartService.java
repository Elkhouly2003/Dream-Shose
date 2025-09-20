package com.example.dream_shops.service.cart;

import com.example.dream_shops.exceptions.ResourceNotFoundException;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.model.CartItem;
import com.example.dream_shops.model.Product;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.repository.CartRepository;
import com.example.dream_shops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;



    @Override
    public Cart addCart() {
        return cartRepository.save(new Cart());
    }


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found with ID: " + productId);
        }

        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));


        CartItem existingCartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst()
                .orElse(null);

        CartItem cartItemToSave;
        if (existingCartItem == null) {

            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setUnitPrice(product.getPrice());
            newCartItem.setCart(cart);


            cartItemToSave = newCartItem;
        } else {
            existingCartItem.setUnitPrice(product.getPrice());
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemToSave = existingCartItem;
        }

        cartItemRepository.save(cartItemToSave);

    }
    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
    }



    @Override
    public BigDecimal getCartPrice(Long id) {
         Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
         return cart.getItems().stream()
                  .map(CartItem::getCartItemPrice)
                  .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void removeItemFromCart(Long cartId, Long itemId) {
          Cart cart = getCart(cartId);
          cart.getItems().stream().filter(item -> item.getId().equals(itemId))
                                  .findFirst()
                                  .ifPresent( item ->{
                                                     cartItemRepository.delete(item) ;
                                                     cart.getItems().remove(item) ;
                                  });

          cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = getCartPrice(cart.getId());
        cart.setTotalPrice(totalAmount);

        return cartRepository.save(cart);
    }


}
