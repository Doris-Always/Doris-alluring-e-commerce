package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class UpdateCartProductReq {
    private Long cartProductId;
    private int quantity;
}
