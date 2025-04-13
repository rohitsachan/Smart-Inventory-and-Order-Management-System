package com.example.ecommerce.rest.order.entity;

import com.example.ecommerce.rest.product.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Schema(description = "OrderItem entity representing individual items within an order")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the order item", example = "1")
    private Long id;
    
    @ManyToOne
    @Schema(description = "Product being ordered")
    private Product product;
    
    @ManyToOne
    @JsonIgnore
    private Order order;
    
    @Schema(description = "Quantity of the product ordered", example = "2")
    private Integer quantity;
    
    @Schema(description = "Price of the product at the time of order", example = "29.99")
    private Double price;
    
    @Schema(description = "Calculated subtotal for this item (quantity * price)", example = "59.98")
    public Double getSubtotal() {
        return price * quantity;
    }
}