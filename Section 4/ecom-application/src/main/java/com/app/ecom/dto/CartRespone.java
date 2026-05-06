package com.app.ecom.dto;

import com.app.ecom.model.Product;
import com.app.ecom.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class CartRespone {

    private Long cartId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer totalPrice;

}
