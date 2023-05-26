package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.model.Cart;
public interface CartService {
    void addToCart(Cart cart);
    Cart viewAllProductInCart();
    void removeItemFromCart(Long cartProductId);
}
