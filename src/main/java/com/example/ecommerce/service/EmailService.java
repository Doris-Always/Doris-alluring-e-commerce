package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.OtpRequest;

public interface EmailService {
//    void sendOTP(OtpRequest otpRequest);
    void sendOTP(String receiver,String message);

    void sendPaymentConfirmationEmail(String receiver);
}
