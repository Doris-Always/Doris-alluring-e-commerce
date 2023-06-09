package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.EmailDetailDTO;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    void sendOTP(String receiver);
    void sendPaymentConfirmationEmail(String receiver);
}
