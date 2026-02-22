package com.akshay.smart_inventory.service.product;

import com.akshay.smart_inventory.dto.request.ProductRequest;
import com.akshay.smart_inventory.dto.response.ProductResponse;

import java.util.List;

public interface IProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}