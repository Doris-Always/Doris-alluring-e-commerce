package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class RatingRequest {
    private int rating;
    private Long userId;
    private Long productId;
}
