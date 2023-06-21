package com.example.ecommerce.service;

import com.example.ecommerce.dto.request.CreateOrderItemRequest;
import com.example.ecommerce.dto.request.UpdateOrderItemRequest;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(CreateOrderItemRequest createOrderItemRequest);
    OrderItem getOrderItemById(Long orderItemId);
    void deleteOrderItemById(Long orderItemId);
    OrderItem getOrderItemByName(String productName);
    void deleteAllOrderItem();
    OrderItem getOrderItemsByOrderHistory(OrderHistory orderHistory);
    OrderItem updateOrderItem(Long orderItemId, UpdateOrderItemRequest updateOrderItemRequest);
    OrderItem getOrderItemByProduct(Long productId);
    BigDecimal calculateOrderItemTotal(int quantity, BigDecimal price);

    List<OrderItem> getAllOrderItems();
}
