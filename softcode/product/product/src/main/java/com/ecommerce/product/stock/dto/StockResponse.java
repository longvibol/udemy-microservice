package com.ecommerce.product.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private String name;
    private String description;
    private Integer stockQuantity;


}
