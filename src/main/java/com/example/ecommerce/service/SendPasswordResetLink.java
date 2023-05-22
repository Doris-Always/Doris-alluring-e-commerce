package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.ResetLinkReq;
import org.springframework.http.ResponseEntity;

public interface SendPasswordResetLink {
    ResponseEntity<?> sendLink(ResetLinkReq resetLinkReq);
}
