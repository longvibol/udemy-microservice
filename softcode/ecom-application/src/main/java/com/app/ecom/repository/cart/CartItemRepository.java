package com.app.ecom.repository.cart;

import com.app.ecom.model.CartItem;
import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);

    List<CartItem> findByUserId(Long userId);

    void deleteByUser(User user);
}
