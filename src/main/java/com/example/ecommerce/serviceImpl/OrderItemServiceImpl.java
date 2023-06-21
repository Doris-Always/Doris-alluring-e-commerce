package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.CreateOrderItemRequest;
import com.example.ecommerce.dto.request.UpdateOrderItemRequest;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.service.OrderItemService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ProductService productService;

    @Override
    public OrderItem createOrderItem(CreateOrderItemRequest createOrderItemRequest) {
        var foundProduct = productService.findProductById(createOrderItemRequest.getProductId());
        OrderItem orderItem = new OrderItem();
        if (foundProduct !=null){
             orderItem.setProduct(foundProduct);
        }
        orderItem.setOrderHistory(createOrderItemRequest.getOrderHistory());
        orderItem.setPrice(createOrderItemRequest.getPrice());
        orderItem.setSubTotal(calculateOrderItemTotal(createOrderItemRequest.getQuantity(), createOrderItemRequest.getPrice()));
        orderItemRepository.save(orderItem);
        return orderItem;
    }

        @Override
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(()->new IllegalArgumentException("order does not exist"));
    }

    @Override
    public void deleteOrderItemById(Long orderItemId) {
        getOrderItemById(orderItemId);
        orderItemRepository.deleteById(orderItemId);

    }

    @Override
    public OrderItem getOrderItemByName(String productName) {
//       return orderItemRepository.findOrderItemByName(productName);
        return null;
    }

    @Override
    public void deleteAllOrderItem() {
        orderItemRepository.deleteAll();
    }

    @Override
    public OrderItem getOrderItemsByOrderHistory(OrderHistory orderHistory) {
        return orderItemRepository.findOrderItemByOrderHistory(orderHistory);
    }

    @Override
    public OrderItem updateOrderItem(Long orderItemId, UpdateOrderItemRequest updateOrderItemRequest) {
        OrderItem foundOrderItem = orderItemRepository.findById(orderItemId).orElseThrow(()->new IllegalArgumentException("Order item not found"));
        foundOrderItem.setQuantity(updateOrderItemRequest.getQuantity());
        foundOrderItem.setPrice(updateOrderItemRequest.getPrice());
        foundOrderItem.setSubTotal(calculateOrderItemTotal(updateOrderItemRequest.getQuantity(),updateOrderItemRequest.getPrice()));
        return orderItemRepository.save(foundOrderItem);
    }

    @Override
    public OrderItem getOrderItemByProduct(Long productId) {
        return orderItemRepository.findOrderItemByProductId(productId);
    }

    @Override
    public BigDecimal calculateOrderItemTotal(int quantity, BigDecimal price) {
        BigDecimal castedQuantity = new BigDecimal(quantity);
        return price.multiply(castedQuantity);

    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }
}
