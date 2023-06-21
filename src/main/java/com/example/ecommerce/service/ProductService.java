package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest addProductRequest);
    Product saveProduct(Product product);
    void removeProductById(Long productId);
    List<Product> findAllProduct();
    Product findProductById(Long productId);
    Product updateProduct(Long productId,UpdateProductRequest updateProductRequest);
}
