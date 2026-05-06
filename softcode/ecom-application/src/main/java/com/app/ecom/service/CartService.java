package com.app.ecom.service;

import com.app.ecom.dto.CartItemRequest;
import com.app.ecom.dto.CartRespone;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductsRepository;
import com.app.ecom.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ProductsRepository productsRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public boolean addToCart(String userId, CartItemRequest request) {

        // Validation
        // Look for product
        Optional<Product> productOpt = productsRepository.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;
        Product product = productOpt.get();

        // Check stock
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        // check and find user
        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        // first we need to check the cart existing or not if have we add the item to the cart
        CartItem existingCardItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCardItem != null){
            // Update the quantity
            existingCardItem.setQuantity(existingCardItem.getQuantity() + request.getQuantity());

            // Update price : existing Item price * qty
           existingCardItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCardItem.getQuantity())));

           // save
            cartItemRepository.save(existingCardItem);

        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        // Deduct stock quantity
        product.setStockQuantity(
                product.getStockQuantity() - request.getQuantity()
        );

        // Save updated product
        productsRepository.save(product);
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {

        Optional<Product> productOpt =
                productsRepository.findById(productId);

        Optional<User> userOpt =
                userRepository.findById(Long.valueOf(userId));

        if (productOpt.isEmpty() || userOpt.isEmpty()) {
            return false;
        }

        cartItemRepository.deleteByUserAndProduct(
                userOpt.get(),
                productOpt.get()
        );

        return true;
    }

    public boolean deleteItemFromCartOption1(String userId, Long productId) {

        Long uid = Long.valueOf(userId);

        // Find user
        Optional<User> userOpt = userRepository.findById(uid);
        if (userOpt.isEmpty()) {
            return false;
        }

        // Find cart item
        Optional<CartItem> cartItemOpt =
                cartItemRepository.findByUserIdAndProductId(uid, productId);

        if (cartItemOpt.isEmpty()) {
            return false;
        }

        CartItem cartItem = cartItemOpt.get();

        // Find product
        Optional<Product> productOpt = productsRepository.findById(productId);

        if (productOpt.isEmpty()) {
            return false;
        }

        Product product = productOpt.get();

        // Increase product stock by 1
        product.setStockQuantity(
                product.getStockQuantity() + 1
        );

        productsRepository.save(product);

        // Reduce cart quantity
        if (cartItem.getQuantity() > 1) {

            cartItem.setQuantity(
                    cartItem.getQuantity() - 1
            );

            cartItemRepository.save(cartItem);

        } else {

            // quantity == 1
            cartItemRepository.delete(cartItem);
        }

        return true;
    }

    public List<CartRespone> getCartById(String userId) {

        Long userIdLong = Long.valueOf(userId);

        Optional<User> userOpt = userRepository.findById(userIdLong);

        // Check if user exists
        if (userOpt.isEmpty()) {
            return Collections.emptyList();
        }

        User user = userOpt.get();

        // Get all cart items for this user
        List<CartItem> cartItems = cartItemRepository.findByUser(user);

        // Convert CartItem list -> CartRespone list
        List<CartRespone> responses = new ArrayList<>();

        for (CartItem cartItem : cartItems) {

            CartRespone response = new CartRespone();

            response.setCartId(cartItem.getId());

            if (cartItem.getProduct() != null) {
                response.setProductId(cartItem.getProduct().getId());
                response.setProductName(cartItem.getProduct().getName());
            }

            response.setQuantity(cartItem.getQuantity());

            response.setTotalPrice(
                    cartItem.getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                            .intValue()
            );

            responses.add(response);
        }

        return responses;
    }


}



















