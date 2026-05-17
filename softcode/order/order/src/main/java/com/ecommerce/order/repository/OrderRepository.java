package com.ecommerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
