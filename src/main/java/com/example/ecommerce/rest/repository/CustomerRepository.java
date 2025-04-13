package com.example.ecommerce.rest.repository;

import com.example.ecommerce.rest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}