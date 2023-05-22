package com.example.ecommerce.service;

import com.example.ecommerce.model.CartProduct;
import com.example.ecommerce.model.Product;

public interface CartProductService {
    CartProduct addCartProduct(CartProduct cartProduct);

    CartProduct findProductById(long productId);
}
