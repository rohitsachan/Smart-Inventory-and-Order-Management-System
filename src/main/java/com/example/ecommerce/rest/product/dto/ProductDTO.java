package com.example.ecommerce.rest.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Product data transfer object for API operations")
public class ProductDTO {
    @Schema(description = "Product ID (null for creation)", example = "1")
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the product", example = "Wireless Mouse", required = true)
    private String name;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Detailed description of the product", example = "Ergonomic wireless mouse with 6 programmable buttons", required = true)
    private String description;
    
    @NotBlank(message = "Category is required")
    @Schema(description = "Category of the product", example = "Electronics", required = true)
    private String category;
    
    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Schema(description = "Price of the product", example = "29.99", required = true)
    private Double price;
    
    @NotNull(message = "Stock level is required")
    @Min(value = 0, message = "Stock level must be greater than or equal to 0")
    @Schema(description = "Current stock level", example = "100", required = true)
    private Integer stockLevel;
    
    @NotNull(message = "Minimum stock level is required")
    @Min(value = 0, message = "Minimum stock level must be greater than or equal to 0")
    @Schema(description = "Minimum stock level before alerts are triggered", example = "10", required = true)
    private Integer minStockLevel;
}