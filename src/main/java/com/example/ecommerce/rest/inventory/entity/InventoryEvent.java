package com.example.ecommerce.rest.inventory.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Schema(description = "InventoryEvent entity representing stock level changes and adjustments")
public class InventoryEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the inventory event", example = "1")
    private Long id;
    
    @Schema(description = "ID of the product affected by this event", example = "1")
    private Long productId;
    
    @Schema(
        description = "Change in quantity (positive for additions, negative for removals)",
        example = "10"
    )
    private Integer quantityChange;
    
    @Schema(
        description = "Type of inventory event",
        example = "STOCK_IN",
        allowableValues = {"STOCK_IN", "STOCK_OUT", "ADJUSTMENT"}
    )
    private String eventType;
    
    @Schema(
        description = "Timestamp when the event occurred",
        example = "2025-04-13T14:30:00"
    )
    private LocalDateTime timestamp;
    
    @Schema(
        description = "Reason for the inventory change",
        example = "Restock from supplier ABC123"
    )
    private String reason;
}