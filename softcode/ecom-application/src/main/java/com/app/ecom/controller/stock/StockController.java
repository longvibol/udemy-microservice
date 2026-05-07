package com.app.ecom.controller.stock;

import com.app.ecom.dto.stock.StockResponse;
import com.app.ecom.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockProducts(
            @PathVariable Long id) {

        StockResponse response =
                productService.findStockQuantityByProductId(id);

        if (response == null) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
