package com.meghana.practice.order_management.service;

import com.meghana.practice.order_management.exception.InvalidOrderException;
import com.meghana.practice.order_management.exception.OrderNotFoundException;
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
        validateOrder(order);
        order.setOrderId(UUID.randomUUID().toString());

        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(String orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(orderId));
    }

    @Override
    public Order updateOrder(String orderId, Order updatedOrder) {
        validateOrder(updatedOrder);
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

    private void validateOrder(Order order) {

        if(order.getCustomerId() == null ||
                order.getCustomerId().isBlank()) {
            throw new InvalidOrderException("Customer ID is required");
        }

        if(order.getProductName() == null ||
                order.getProductName().isBlank()) {
            throw new InvalidOrderException("Product Name is required");
        }

        if(order.getQuantity() == null ||
                order.getQuantity() <= 0) {
            throw new InvalidOrderException("Quantity must be greater than 0");
        }

        if(order.getPrice() == null ||
                order.getPrice() <= 0) {
            throw new InvalidOrderException("Price must be greater than 0");
        }
    }
}
