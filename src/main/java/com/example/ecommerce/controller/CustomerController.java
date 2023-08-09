package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.service.CartProductService;
import com.example.ecommerce.service.ChangePasswordService;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ecommerce")
public class CustomerController {
    @Autowired
    UserService userService;



    @Autowired
    CartProductService cartProductService;



    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart( @PathVariable("id") Long id,@RequestBody AddToCartRequest cartRequest){
       return new ResponseEntity<>(userService.addToCart(id,cartRequest),HttpStatus.OK);
    }

    @PostMapping("/updateUserInfo/{id}")
    public ResponseEntity<?> updateUserInfo(@PathVariable("id") Long id,@RequestBody UpdateUserRequest updateUserRequest){
        return new ResponseEntity<>(userService.updateUserInfo(id,updateUserRequest),HttpStatus.OK);
    }

    @DeleteMapping("/removeItem/{id}/{productId}")
    public void removeItemFromCart(@PathVariable Long id,@PathVariable Long productId){
        userService.removeItemFromCart(id,productId);
    }
    @PostMapping("/updateCartProduct")
    public ResponseEntity<?> updateCartProduct(@RequestBody UpdateCartProductReq updateCartProductReq){
            return new ResponseEntity<>( cartProductService.updateCartProduct(updateCartProductReq),HttpStatus.OK);
    }
    @PostMapping("/order")
    public ResponseEntity<?> orderProduct(@RequestBody OrderProductRequest orderProductRequest){
        return new ResponseEntity<>(userService.orderProduct(orderProductRequest),HttpStatus.OK);
    }
    @DeleteMapping("/cancelOrder/{orderId}")
    public void cancelOrder(@PathVariable Long orderId){
        userService.cancelOrder(orderId);
    }

    @PostMapping("/makePayment/{id}")
    public ResponseEntity<?> makePayment(@PathVariable Long id, @RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(userService.makePayment(id,paymentRequest),HttpStatus.OK);
    }
}
