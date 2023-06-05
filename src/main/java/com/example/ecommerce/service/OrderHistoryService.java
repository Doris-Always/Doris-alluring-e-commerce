package com.example.ecommerce.service;

import com.example.ecommerce.model.OrderHistory;

import java.util.List;

public interface OrderHistoryService {
    OrderHistory saveOrder(OrderHistory orderHistory);
    void deleteOrderHistories();
    List<OrderHistory> getAllOrderHistory();
}
