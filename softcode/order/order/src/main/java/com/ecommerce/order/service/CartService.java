package com.ecommerce.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

	private final CartItemRepository cartItemRepository;

	public boolean addToCart(Long userId, CartItemRequest request) {

		/*
		 * // Validation // Look for product Optional<Product> productOpt =
		 * productsRepository.findById(request.getProductId()); if
		 * (productOpt.isEmpty()) return false; Product product = productOpt.get();
		 * 
		 * // Check stock if (product.getStockQuantity() < request.getQuantity()) return
		 * false;
		 * 
		 * // check and find user Optional<User> userOpt =
		 * userRepository.findById(Long.valueOf(userId)); if (userOpt.isEmpty()) return
		 * false;
		 * 
		 * User user = userOpt.get();
		 * 
		 */

		CartItem existingCardItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());

		if (existingCardItem != null) {
			// Update the quantity
			existingCardItem.setQuantity(existingCardItem.getQuantity() + request.getQuantity());

			// Update price : existing Item price * qty
			existingCardItem.setPrice(BigDecimal.valueOf(1000));

			// save
			cartItemRepository.save(existingCardItem);

		} else {
			// Create new cart item

			CartItem cartItem = new CartItem();
			cartItem.setUserId(userId);
			cartItem.setProductId(request.getProductId());
			cartItem.setQuantity(request.getQuantity());
			cartItem.setPrice(BigDecimal.valueOf(1000.00));
			cartItemRepository.save(cartItem);
		}

		return true;
	}

	public boolean deleteItemFromCart(Long userId, Long productId) {

		CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

		if (cartItem != null) {
			cartItemRepository.delete(cartItem);
			return true;
		}

		return false;
	}

	public List<CartItem> getCart(Long userId) {

		return cartItemRepository.findByUserId(userId);
	}

	public void clearCart(Long userId) {
		cartItemRepository.deleteByUserId(userId);
	}
	
	public CartItem getCartItemByProductId(Long userId, Long productId) {
	    return cartItemRepository.findByUserIdAndProductId(userId, productId);
	}
	
	
	public CartItem updateCartItem(
	        Long userId,
	        Long productId,
	        Integer quantity) {

	    CartItem cartItem =
	            cartItemRepository.findByUserIdAndProductId(
	                    userId,
	                    productId);

	    if (cartItem == null) {
	        return null;
	    }

	    cartItem.setQuantity(quantity);

	    // Optional: recalculate total price
	    cartItem.setPrice(
	            BigDecimal.valueOf(1000)
	    );

	    return cartItemRepository.save(cartItem);
	}
}
