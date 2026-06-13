package com.meghana.practice.order_management.service;

import com.meghana.practice.order_management.model.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Order order);

    Order getOrder(String orderId);

    Order updateOrder(String orderId, Order order);

    void deleteOrder(String orderId);

    List<Order> getAllOrders();
}
