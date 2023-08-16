package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class ConfirmationTokenRequest {
    private String token;
    private String email;
}
