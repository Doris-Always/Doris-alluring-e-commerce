package com.example.ecommerce.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResendTokenRequest {
    @NotNull(message = "this field is required")
    @NotEmpty(message = "this field is required")
    private String email;
}
