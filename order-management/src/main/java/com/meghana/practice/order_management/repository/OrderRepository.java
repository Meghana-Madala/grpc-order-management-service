package com.meghana.practice.order_management.repository;

import com.meghana.practice.order_management.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}