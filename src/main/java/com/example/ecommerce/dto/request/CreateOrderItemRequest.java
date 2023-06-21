package com.example.ecommerce.dto.request;

import com.example.ecommerce.model.OrderHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderItemRequest {
    private Long productId;
    private BigDecimal subTotal;
    private BigDecimal price;
    private int quantity;
    private OrderHistory orderHistory;
}
