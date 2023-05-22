package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.ChangePasswordReq;
import org.springframework.http.ResponseEntity;

public interface ChangePasswordService {
    ResponseEntity<?> changePassword(ChangePasswordReq changePasswordReq);
}
