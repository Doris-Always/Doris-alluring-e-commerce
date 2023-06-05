package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.repository.OrderHistoryRepository;
import com.example.ecommerce.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
