package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class ChangePasswordReq {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
