package com.app.ecom.service.product;

import com.app.ecom.dto.product.ProductRequest;
import com.app.ecom.dto.product.ProductResponse;
import com.app.ecom.dto.stock.StockResponse;
import com.app.ecom.model.Product;
import com.app.ecom.repository.product.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductsRepository productsRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();

        updateProductFromProductRequest(product, productRequest);
        Product saveProduct = productsRepository.save(product);

        return mapToProductResponse(product);

    }

    private ProductResponse mapToProductResponse(Product saveProduct) {

        ProductResponse response = new ProductResponse();

        response.setId(saveProduct.getId());
        response.setName(saveProduct.getName());
        response.setDescription(saveProduct.getDescription());
        response.setPrice(saveProduct.getPrice());
        response.setStockQuantity(saveProduct.getStockQuantity());
        response.setCategory(saveProduct.getCategory());
        response.setImageUrl(saveProduct.getImageUrl());
        response.setActive(saveProduct.getActive());

        return response;

    }

    private void updateProductFromProductRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());

    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productsRepository.findById(id)
                .map(existingProduct -> {
//                  we found it then we want to update it : convert from productRequest to product
                    updateProductFromProductRequest(existingProduct, productRequest);

//                  save productRequest to database

                    Product saveProduct = productsRepository.save(existingProduct);

//                  Product Respond back
                    return mapToProductResponse(saveProduct);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productsRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> getProductById(Long id) {
        return productsRepository.findById(id).map(this::mapToProductResponse);
    }

    public boolean deleteProduct(Long id){

        return productsRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productsRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {

        return productsRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());

    }

    public StockResponse findStockQuantityByProductId(Long productId) {

        Optional<Product> productOpt = productsRepository.findById(productId);

        if (productOpt.isEmpty()) {
            return null;
        }

        Product product = productOpt.get();

        StockResponse response = new StockResponse();
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setStockQuantity(product.getStockQuantity());

        return response;
    }
}
