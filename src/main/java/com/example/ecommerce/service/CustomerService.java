package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.model.Customer;

import java.util.List;
import java.util.Optional;


public interface CustomerService {
     Customer register(UserRegisterRequest registerRequest);
     LoginResponse login(LoginRequest loginRequest);
     String addToCart(Long id, AddToCartRequest cartRequest);

     List<Customer> findAllCustomer();
     Customer findCustomerById(Long customerId);
     Long count();

     void removeItemFromCart(Long customerId,Long productId);

}
