package com.example.ecommerce.service;

import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;

import java.util.List;

public interface CartProductService {
    CartProduct addCartProduct(CartProduct cartProduct);

    CartProduct findProductById(long productId);

    List<CartProduct> getAllCartProduct();
    void deleteProductById(Long productId);

   void deleteAllProduct();

    void removeCartProducts(Long productId);
}
