package com.example.ecommerce.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateOrderItemRequest {
    private BigDecimal price;
    private BigDecimal subTotal;
    private int quantity;
}
