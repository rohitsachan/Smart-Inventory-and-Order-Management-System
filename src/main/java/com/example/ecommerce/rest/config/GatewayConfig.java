package com.example.ecommerce.rest.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            // Product Service Routes
            .route("product_service", r -> r.path("/api/v1/products/**")
                .uri("lb://product-service"))
                
            // Inventory Service Routes
            .route("inventory_service", r -> r.path("/api/v1/inventory/**")
                .uri("lb://inventory-service"))
                
            // Order Service Routes
            .route("order_service", r -> r.path("/api/v1/orders/**")
                .uri("lb://order-service"))
                
            // Customer Service Routes
            .route("customer_service", r -> r.path("/api/v1/customers/**")
                .uri("lb://customer-service"))
                
            // Payment Service Routes
            .route("payment_service", r -> r.path("/api/v1/payments/**")
                .uri("lb://payment-service"))
                
            // Auth Service Routes
            .route("auth_service", r -> r.path("/api/v1/auth/**")
                .uri("lb://auth-service"))
            .build();
    }
}