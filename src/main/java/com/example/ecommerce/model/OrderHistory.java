package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long orderId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String productName;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Address_id")
    private Address deliveryAddress;
    @OneToMany(mappedBy = "orderHistory", cascade = CascadeType.ALL)
    List<OrderItem> orderItems;
    @CreationTimestamp
    private Instant orderDate;



}
