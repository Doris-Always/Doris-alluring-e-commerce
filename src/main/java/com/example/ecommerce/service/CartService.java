package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import org.springframework.http.ResponseEntity;

public interface CartService {
    void addToCart(Cart cart);
    Cart viewAllProductInCart();
}
