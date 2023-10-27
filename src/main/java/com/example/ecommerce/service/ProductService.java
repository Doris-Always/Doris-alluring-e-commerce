package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.AddProductRequest;
import com.example.ecommerce.dto.request.FindAllRequest;
import com.example.ecommerce.dto.request.RatingRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.dto.response.RatingResponse;
import com.example.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest addProductRequest);
    Product saveProduct(Product product);
    void removeProductById(Long productId);
    List<Product> findAllProduct();
    Product findProductById(Long productId);

    Page<Product> findAllProductsWithPaginationAndSortingWithDirection(FindAllRequest findAllProductRequest);

    Product updateProduct(Long productId, UpdateProductRequest updateProductRequest);

}
