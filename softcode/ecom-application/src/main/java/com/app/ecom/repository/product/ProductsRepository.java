package com.app.ecom.repository.product;

import com.app.ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("""
        SELECT p FROM products p 
        WHERE p.active = true 
        AND p.stockQuantity > 0 
        AND (
            :keyword IS NULL OR :keyword = '' 
            OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Product> searchProducts(@Param("keyword") String keyword);

}
