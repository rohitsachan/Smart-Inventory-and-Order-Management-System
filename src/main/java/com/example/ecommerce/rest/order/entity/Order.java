package com.example.ecommerce.rest.order.entity;

import com.example.ecommerce.rest.customer.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Schema(description = "Order entity representing customer purchases")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;
    
    @ManyToOne
    @Schema(description = "Customer who placed the order")
    private Customer customer;
    
    @Schema(description = "Date and time when the order was placed", example = "2025-04-13T14:30:00")
    private LocalDateTime orderDate = LocalDateTime.now();
    
    @Schema(
        description = "Current status of the order", 
        example = "PENDING",
        allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"}
    )
    private String status = "PENDING";
    
    @Schema(description = "Total amount of the order", example = "299.99")
    private Double totalAmount = 0.0;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @Schema(description = "List of items in the order")
    private List<OrderItem> items = new ArrayList<>();
}