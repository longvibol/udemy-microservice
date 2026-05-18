package com.ecommerce.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.model.Product;
import com.ecommerce.order.model.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    List<CartItem> findByUserId(Long userId);

    void deleteByUser(User user);
}
