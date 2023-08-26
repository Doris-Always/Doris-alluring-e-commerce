package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.EmailRequest;
import com.example.ecommerce.dto.request.OtpRequest;

public interface EmailService {
//    void sendOTP(OtpRequest otpRequest);
    void sendOTP(EmailRequest emailRequest);

    void sendPaymentConfirmationEmail(String receiver);
}
