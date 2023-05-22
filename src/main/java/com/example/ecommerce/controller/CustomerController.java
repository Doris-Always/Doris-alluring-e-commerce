package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.*;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.ChangePasswordService;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ecommerce")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @Autowired
    ChangePasswordService changePasswordService;

    @Autowired
    ForgetPasswordService forgetPasswordService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerRequest){
        return new ResponseEntity<>(customerService.register(registerRequest), HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(customerService.login(loginRequest),HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReq changePasswordReq){
        return new ResponseEntity<>(changePasswordService.changePassword(changePasswordReq),HttpStatus.OK);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPasswordReq resetPasswordReq){
        return new ResponseEntity<>(forgetPasswordService.resetPassword(resetPasswordReq),HttpStatus.OK);
    }
    @PostMapping("/addToCart/{id}")
    public ResponseEntity<?> addToCart( @PathVariable("id") Long id,@RequestBody AddToCartRequest cartRequest){
       return new ResponseEntity<>(customerService.addToCart(id,cartRequest),HttpStatus.OK);
    }
    @GetMapping("/findCustomer/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.findCustomerById(id),HttpStatus.OK);
    }
    @GetMapping("/findAllCustomer")
    public ResponseEntity<?> findAllCustomer(){
        return new ResponseEntity<>(customerService.findAllCustomer(),HttpStatus.OK);
    }


}
