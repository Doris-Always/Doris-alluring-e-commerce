package com.example.ecommerce.controller;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import com.example.ecommerce.dto.request.LoginRequest;
import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.dto.request.UserRegisterRequest;
import com.example.ecommerce.service.ChangePasswordService;
import com.example.ecommerce.service.ForgetPasswordService;
import com.example.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ForgetPasswordService forgetPasswordService;
    @Autowired
    ChangePasswordService changePasswordService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest registerRequest){
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.OK);

    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.login(loginRequest),HttpStatus.OK);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReq changePasswordReq){
        return new ResponseEntity<>(changePasswordService.changePassword(changePasswordReq),HttpStatus.OK);
    }
    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPasswordReq resetPasswordReq){
        return new ResponseEntity<>(forgetPasswordService.resetPassword(resetPasswordReq),HttpStatus.OK);
    }
}
