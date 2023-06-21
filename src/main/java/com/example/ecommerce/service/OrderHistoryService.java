package com.example.ecommerce.service;

import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderHistoryService {
    OrderHistory saveOrder(OrderHistory orderHistory);
    void deleteOrderHistories();
    List<OrderHistory> getAllOrderHistory();
    void deleteOrderById(Long orderId);

    Optional<OrderHistory> findById(Long orderId);
    User getUserByOrder(OrderHistory orderHistory);
}
