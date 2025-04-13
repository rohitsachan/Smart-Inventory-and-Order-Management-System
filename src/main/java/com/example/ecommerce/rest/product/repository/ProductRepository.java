package com.example.ecommerce.rest.product.repository;

import com.example.ecommerce.rest.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStockLevelLessThanEqual(Integer level);
}