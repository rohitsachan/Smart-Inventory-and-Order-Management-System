package com.example.ecommerce.rest.product.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Product entity representing items available for sale")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;
    
    @Schema(description = "Name of the product", example = "Wireless Mouse")
    private String name;
    
    @Schema(description = "Detailed description of the product", example = "Ergonomic wireless mouse with 6 programmable buttons")
    private String description;
    
    @Schema(description = "Category of the product", example = "Electronics")
    private String category;
    
    @Schema(description = "Price of the product", example = "29.99")
    private Double price;
    
    @Schema(description = "Current stock level", example = "100")
    private Integer stockLevel;
    
    @Schema(description = "Minimum stock level before alerts are triggered", example = "10")
    private Integer minStockLevel;
}