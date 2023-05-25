package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.dto.response.LoginResponse;
import com.example.ecommerce.model.User;

import java.util.List;


public interface UserService {
     User register(UserRegisterRequest registerRequest);
     LoginResponse login(LoginRequest loginRequest);
     String addToCart(Long id, AddToCartRequest cartRequest);

     List<User> findAllUser();
     User findUserById(Long userId);
     User findUserByFirstName(String firstName);

     User updateUserInfo(Long userId,UpdateUserRequest updateUserRequest);
     Long count();

     void removeItemFromCart(Long userId, Long productId);

}
