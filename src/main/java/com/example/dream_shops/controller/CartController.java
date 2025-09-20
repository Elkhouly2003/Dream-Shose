package com.example.dream_shops.controller;

import com.example.dream_shops.dto.CartDto;
import com.example.dream_shops.model.Cart;
import com.example.dream_shops.repository.CartItemRepository;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.cart.CartItemService;
import com.example.dream_shops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse>getCart(@PathVariable Long id){
        try {
            Cart cart = cartService.getCart(id);

            if(cart != null) {
                CartDto cartDto = CartDto.cartToCartDto(cart);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Cart Found Success", cartDto));
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Cart Not Found", "not found"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), "error at loading" ));
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> addCart(){
        try {
          Cart newCart =   cartService.addCart();
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Adding new Cart Success !",newCart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Request Failed !", "Failed to add Cart")) ;
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId , @RequestParam Long productId , @RequestParam int quantity){

        try {
            cartService.addItemToCart(cartId ,productId,quantity);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(" Request Success ! ","Item Added Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Request Failed !", e.getMessage())) ;
        }

    }

    @DeleteMapping("clear/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id){
        cartService.clearCart(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Clearing Cart Success !","Cart Clear Success"));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse> updateItemQuantity(@RequestParam Long cartId , @RequestParam Long itemId , int quantity){
        try {
            cartItemService.updateItemQuantity(cartId ,itemId,quantity);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item Updated Success !","Item Updated Success"));

        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Request Failed !", e.getMessage())) ;
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<ApiResponse> removeItemFromCart(@RequestParam Long cartId , @RequestParam Long itemId ){

        try {
                cartService.removeItemFromCart(cartId ,itemId);
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Item Removed Success !","Item Removed Success"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Request Failed !", e.getMessage()));
        }

    }


}
