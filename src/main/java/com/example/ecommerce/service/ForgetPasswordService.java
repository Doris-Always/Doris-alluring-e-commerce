package com.example.ecommerce.service;
import com.example.ecommerce.dto.request.ResetPasswordReq;
import org.springframework.http.ResponseEntity;

public interface ForgetPasswordService {

    ResponseEntity<?> resetPassword(ResetPasswordReq forgotPasswordReq);
}
