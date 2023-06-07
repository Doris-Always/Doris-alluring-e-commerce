package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.UpdateOrderItemRequest;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemTest {
    @Mock
    OrderItemRepository orderItemRepository;

    @InjectMocks
    OrderItemServiceImpl orderItemService;




    @BeforeEach
    void setUp() {

    }

    @Test
    public void testCreateOrderItem(){
        Product product = mock(Product.class);
        OrderHistory orderHistory = mock(OrderHistory.class);
        OrderItem orderItem = new OrderItem(1L,product,3,BigDecimal.valueOf(100),BigDecimal.valueOf(300),orderHistory);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        orderItemService.createOrderItem(orderItem);
        verify(orderItemRepository).save(orderItem);
        assertEquals(BigDecimal.valueOf(300),orderItem.getSubTotal());
    }
    @Test
    public void testUpdateOrderItem(){
        Product product = mock(Product.class);
        OrderHistory orderHistory = mock(OrderHistory.class);
        UpdateOrderItemRequest updateOrderItemRequest = new UpdateOrderItemRequest();
        updateOrderItemRequest.setPrice(BigDecimal.valueOf(100));
        updateOrderItemRequest.setQuantity(2);

        Long orderItemId = 1L;
        OrderItem foundOrderItem = new OrderItem();
        foundOrderItem.setOrderItemId(orderItemId);
        foundOrderItem.setQuantity(1);
        foundOrderItem.setPrice(BigDecimal.valueOf(50));
        foundOrderItem.setSubTotal(BigDecimal.valueOf(50));
        foundOrderItem.setOrderHistory(orderHistory);
        foundOrderItem.setProduct(product);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(foundOrderItem);
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(foundOrderItem));

        OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItemId,updateOrderItemRequest);
        verify(orderItemRepository, times(1)).save(updatedOrderItem);
        assertEquals(updateOrderItemRequest.getQuantity(), updatedOrderItem.getQuantity());

    }
    @Test
    void testThatOrderCanBeRetrievedByOrderHistory(){
        OrderHistory orderHistory = new OrderHistory();
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findOrderItemByOrderHistory(orderHistory)).thenReturn(orderItem);
        var foundOrderItem = orderItemService.getOrderItemsByOrderHistory(orderHistory);
        verify(orderItemRepository,times(1)).findOrderItemByOrderHistory(orderHistory);
        assertEquals(orderItem,foundOrderItem);

    }
}
