package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long productId;
    private long cartId;
    private String productName;
    private  int quantity;
}
