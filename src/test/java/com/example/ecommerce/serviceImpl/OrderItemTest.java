package com.example.ecommerce.serviceImpl;

import com.example.ecommerce.dto.request.CreateOrderItemRequest;
import com.example.ecommerce.dto.request.UpdateOrderItemRequest;
import com.example.ecommerce.model.OrderHistory;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Mock
    ProductService productService;
    @Captor
    private ArgumentCaptor<OrderItem> orderItemCaptor;

    @BeforeEach
    void setUp() {

    }

//    @Test
//    public void testCreateOrderItem(){
//        Product product = mock(Product.class);
//        OrderHistory orderHistory = mock(OrderHistory.class);
//        OrderItem orderItem = new OrderItem(1L,product,3,BigDecimal.valueOf(100),BigDecimal.valueOf(300),orderHistory);
//        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
//        orderItemService.createOrderItem(orderItem);
//        verify(orderItemRepository).save(orderItem);
//        assertEquals(BigDecimal.valueOf(300),orderItem.getSubTotal());
//    }
    @Test
    public void testCreateOrderItem(){
        Product product = mock(Product.class);
        OrderHistory orderHistory = mock(OrderHistory.class);
        CreateOrderItemRequest createOrderItemRequest = new CreateOrderItemRequest(1L,BigDecimal.valueOf(300),BigDecimal.valueOf(100),3,orderHistory);
        OrderItem orderItem = new OrderItem(1L,product,3,BigDecimal.valueOf(100),BigDecimal.valueOf(300),orderHistory);
        when(productService.findProductById(product.getId())).thenReturn(product);
        when(orderItemRepository.save(orderItemCaptor.capture())).thenReturn(orderItem);
         var actualOrderItem = orderItemService.createOrderItem(createOrderItemRequest);
         verify(productService).findProductById(product.getId());
        verify(orderItemRepository).save(orderItemCaptor.capture());
        OrderItem capturedOrderItem = orderItemCaptor.getValue();
        assertEquals(actualOrderItem,orderItem);
        assertEquals(capturedOrderItem,actualOrderItem);

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
