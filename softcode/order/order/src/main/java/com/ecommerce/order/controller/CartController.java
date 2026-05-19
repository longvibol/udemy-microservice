package com.ecommerce.order.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    
    @GetMapping("/items/{productId}")
    public ResponseEntity<CartItem> getCartItemByProductId(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long productId) {

        CartItem cartItem = cartService.getCartItemByProductId(userId, productId);

        return cartItem != null
                ? ResponseEntity.ok(cartItem)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") Long userId,
            @RequestBody CartItemRequest request) {

        if (!cartService.addToCart(userId, request)) {
            return ResponseEntity.badRequest()
                    .body("Failed to add item to cart.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Item added to cart successfully.");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeFromCart(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long productId) {
//        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        boolean deleted = cartService.deleteItemFromCart(userId, productId);

        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") Long userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }
    
    
    @PutMapping("/items/{productId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestHeader("X-User-ID") Long userId,
            @PathVariable Long productId,
            @RequestBody CartItemRequest request) {

        CartItem updatedItem = cartService.updateCartItem(
                userId,
                productId,
                request.getQuantity());

        return updatedItem != null
                ? ResponseEntity.ok(updatedItem)
                : ResponseEntity.notFound().build();
    }

}





























