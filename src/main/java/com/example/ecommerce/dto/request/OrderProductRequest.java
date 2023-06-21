package com.example.ecommerce.dto.request;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderProductRequest {
    private Long userId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private String deliveryAddress;

}
