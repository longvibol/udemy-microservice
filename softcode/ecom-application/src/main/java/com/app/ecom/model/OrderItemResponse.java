package com.app.ecom.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemResponse {

    private String productName;
    private BigDecimal price;
    private int quantity;
}