package com.meghana.practice.order_management.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Order {
    @Id
    private String orderId;

    private String customerId;

    private String productName;

    private Integer quantity;

    private Double price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
