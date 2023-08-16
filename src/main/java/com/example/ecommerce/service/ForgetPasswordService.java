package com.example.ecommerce.service;
import com.example.ecommerce.dto.request.ForgotPasswordRequest;
import com.example.ecommerce.dto.request.ResetPasswordReq;
import com.example.ecommerce.dto.response.ForgotPasswordResponse;
import org.springframework.http.ResponseEntity;

public interface ForgetPasswordService {

    ForgotPasswordResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);



    ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq);

}
