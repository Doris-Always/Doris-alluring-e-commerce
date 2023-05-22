package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.AddToCartRequest;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    CartService cartService;
}
