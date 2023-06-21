package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.OrderHistoryRepository;
import com.example.ecommerce.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Override
    public OrderHistory saveOrder(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    @Override
    public void deleteOrderHistories() {
        orderHistoryRepository.deleteAll();
    }

    @Override
    public List<OrderHistory> getAllOrderHistory() {
        return orderHistoryRepository.findAll();
    }

    @Override
    public void deleteOrderById(Long orderId) {
        orderHistoryRepository.deleteById(orderId);
    }

    @Override
    public Optional<OrderHistory> findById(Long orderId) {
        return orderHistoryRepository.findById(orderId);
    }

    @Override
    public User getUserByOrder(OrderHistory orderHistory) {
        return orderHistory.getUser();
    }
}
