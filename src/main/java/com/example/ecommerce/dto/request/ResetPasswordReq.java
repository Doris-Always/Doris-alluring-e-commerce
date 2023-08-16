package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class ResetPasswordReq {
    private String email;
    private String newPassword;
    private String confirmPassword;
    private String token;
}
