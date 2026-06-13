package com.meghana.practice.order_management.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String orderId) {
        super("Order not found with ID: " + orderId);
    }
}
