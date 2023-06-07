package com.example.ecommerce.repository;

import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

    OrderItem findOrderItemByOrderHistory(OrderHistory orderHistory);

    OrderItem findOrderItemByProductId(Long productId);
}
