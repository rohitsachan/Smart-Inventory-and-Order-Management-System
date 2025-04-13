package com.example.ecommerce.rest.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema(description = "Request object for creating new orders")
public class OrderCreateRequest {
    @NotNull(message = "Customer ID is required")
    @Schema(description = "ID of the customer placing the order", example = "1", required = true)
    private Long customerId;
    
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    @Schema(description = "List of items to order", required = true)
    private List<OrderItemRequest> items;

    @Data
    @Schema(description = "Order item details")
    public static class OrderItemRequest {
        @NotNull(message = "Product ID is required")
        @Schema(description = "ID of the product to order", example = "1", required = true)
        private Long productId;
        
        @NotNull(message = "Quantity is required")
        @Schema(description = "Quantity of the product to order", example = "2", required = true)
        private Integer quantity;
    }
}