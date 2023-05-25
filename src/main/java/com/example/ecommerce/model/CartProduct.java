package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class CartProduct{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal pricePerUnit;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal totalPrice;

}

