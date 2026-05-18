package com.app.ecom.controller.cart;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.ecom.dto.cart.CartItemRequest;
import com.app.ecom.model.CartItem;
import com.app.ecom.service.cart.CartService;
import com.ecommerce.dto.cart.CartRespone;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
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
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId) {
//        boolean deleted = cartService.deleteItemFromCart(userId, productId);
        boolean deleted = cartService.deleteItemFromCart(userId, productId);

        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }



    @GetMapping("/items")
    public ResponseEntity<List<CartRespone>> getCartById(
            @RequestHeader("X-User-ID") String userId) {

        List<CartRespone> response =
                cartService.getCartById(userId);

        if (response.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

}







































