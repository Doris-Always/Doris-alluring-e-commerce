package com.example.ecommerce.dto.request;

import com.example.ecommerce.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private int quantity;
    private String productName;
    private BigDecimal pricePerUnit;
    private Category category;
}
