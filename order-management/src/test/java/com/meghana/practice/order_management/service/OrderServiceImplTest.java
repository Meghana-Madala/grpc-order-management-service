package com.meghana.practice.order_management.service;

import com.meghana.practice.order_management.exception.OrderNotFoundException;
import com.meghana.practice.order_management.model.Order;
import com.meghana.practice.order_management.model.OrderStatus;
import com.meghana.practice.order_management.repository.OrderRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldCreateOrderSuccessfully() {

        Order order = Order.builder()
                .customerId("CUST001")
                .productName("Laptop")
                .quantity(1)
                .price(1200.0)
                .build();

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.createOrder(order);

        assertNotNull(savedOrder.getOrderId());
        assertEquals(OrderStatus.CREATED, savedOrder.getStatus());

        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    void getOrder_shouldReturnOrderSuccessfully() {

        String orderId = "123";

        Order order = Order.builder()
                .orderId(orderId)
                .customerId("CUST001")
                .productName("Laptop")
                .quantity(1)
                .price(1200.0)
                .status(OrderStatus.CREATED)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(java.util.Optional.of(order));

        Order result = orderService.getOrder(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());

        verify(orderRepository, times(1))
                .findById(orderId);
    }

    @Test
    void updateOrder_shouldUpdateOrderSuccessfully() {

        String orderId = "123";

        Order existingOrder = Order.builder()
                .orderId(orderId)
                .customerId("CUST001")
                .productName("Laptop")
                .quantity(1)
                .price(1200.0)
                .status(OrderStatus.CREATED)
                .build();

        Order updatedOrder = Order.builder()
                .customerId("CUST002")
                .productName("MacBook")
                .quantity(2)
                .price(2500.0)
                .status(OrderStatus.CONFIRMED)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(java.util.Optional.of(existingOrder));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result =
                orderService.updateOrder(orderId, updatedOrder);

        assertEquals("CUST002", result.getCustomerId());
        assertEquals("MacBook", result.getProductName());
        assertEquals(2, result.getQuantity());
        assertEquals(2500.0, result.getPrice());
        assertEquals(OrderStatus.CONFIRMED, result.getStatus());

        verify(orderRepository, times(1))
                .save(any(Order.class));
    }

    @Test
    void deleteOrder_shouldDeleteOrderSuccessfully() {

        String orderId = "123";

        Order order = Order.builder()
                .orderId(orderId)
                .customerId("CUST001")
                .productName("Laptop")
                .quantity(1)
                .price(1200.0)
                .status(OrderStatus.CREATED)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(java.util.Optional.of(order));

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1))
                .delete(order);
    }

    @Test
    void getOrder_shouldThrowOrderNotFoundException() {

        String orderId = "999";

        when(orderRepository.findById(orderId))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrder(orderId));

        verify(orderRepository, times(1))
                .findById(orderId);
    }
}