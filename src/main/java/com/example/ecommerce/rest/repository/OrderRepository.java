package com.example.ecommerce.rest.repository;

import com.example.ecommerce.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}