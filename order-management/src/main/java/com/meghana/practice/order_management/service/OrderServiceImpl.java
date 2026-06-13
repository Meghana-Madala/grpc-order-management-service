package com.meghana.practice.order_management.service;

import com.meghana.practice.order_management.model.Order;
import com.meghana.practice.order_management.model.OrderStatus;
import com.meghana.practice.order_management.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {

        order.setOrderId(UUID.randomUUID().toString());

        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(String orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));
    }

    @Override
    public Order updateOrder(String orderId, Order updatedOrder) {

        Order existingOrder = getOrder(orderId);

        existingOrder.setCustomerId(updatedOrder.getCustomerId());
        existingOrder.setProductName(updatedOrder.getProductName());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setPrice(updatedOrder.getPrice());
        existingOrder.setStatus(updatedOrder.getStatus());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(String orderId) {

        Order order = getOrder(orderId);

        orderRepository.delete(order);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}
