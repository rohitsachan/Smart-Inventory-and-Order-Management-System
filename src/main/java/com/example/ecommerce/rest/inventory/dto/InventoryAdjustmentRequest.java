package com.example.ecommerce.rest.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Request object for inventory adjustment operations")
public class InventoryAdjustmentRequest {
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    @Schema(description = "Quantity to add, remove, or set", example = "10", required = true)
    private Integer quantity;
    
    @Schema(description = "Reason for the inventory adjustment", example = "Restock from supplier", required = false)
    private String reason;
    
    @Schema(
        description = "Type of adjustment",
        example = "ADD",
        allowableValues = {"ADD", "REMOVE", "SET"},
        required = true
    )
    @NotNull(message = "Adjustment type is required")
    private String adjustmentType;
}