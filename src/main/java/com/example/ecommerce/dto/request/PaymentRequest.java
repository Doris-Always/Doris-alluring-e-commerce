package com.example.ecommerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private String name;
    private BigDecimal amount;
    private String description;
}
