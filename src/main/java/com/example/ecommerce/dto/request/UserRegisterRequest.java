package com.example.ecommerce.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
}
