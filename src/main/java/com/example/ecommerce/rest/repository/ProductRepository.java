package com.example.ecommerce.rest.repository;

import com.example.ecommerce.rest.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}