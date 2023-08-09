package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
     String register(UserRegisterRequest registerRequest);
//     LoginResponse login(LoginRequest loginRequest);
     String login(LoginRequest loginRequest);
     String addToCart(Long id, AddToCartRequest cartRequest);

     List<User> getAllUser();
     Optional<User> findUserById(Long userId);
     User findUserByFirstName(String firstName);
     User findUserByEmail(String email);

     User updateUserInfo(Long userId,UpdateUserRequest updateUserRequest);
     Long count();

     void removeItemFromCart(Long userId, Long productId);

     OrderHistory orderProduct(OrderProductRequest orderProductRequest);
     void cancelOrder(Long orderId);
     String makePayment(Long orderId,PaymentRequest paymentRequest);


}
