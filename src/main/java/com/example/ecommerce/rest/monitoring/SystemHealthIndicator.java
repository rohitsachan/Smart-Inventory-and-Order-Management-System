package com.example.ecommerce.rest.monitoring;

import com.example.ecommerce.rest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemHealthIndicator implements HealthIndicator {
    private final ProductService productService;
    
    @Override
    public Health health() {
        try {
            // Check if the product service is functioning
            productService.getAllProducts();
            
            return Health.up()
                .withDetail("productService", "UP")
                .withDetail("databaseConnection", "UP")
                .build();
        } catch (Exception e) {
            return Health.down()
                .withDetail("productService", "DOWN")
                .withDetail("error", e.getMessage())
                .build();
        }
    }
}