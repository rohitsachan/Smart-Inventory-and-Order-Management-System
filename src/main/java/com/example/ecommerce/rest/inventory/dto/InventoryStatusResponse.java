package com.example.ecommerce.rest.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
@Schema(description = "Response object containing inventory status information")
public class InventoryStatusResponse {
    @Schema(description = "List of products with low stock")
    private List<LowStockItem> lowStockItems;
    
    @Schema(description = "Total number of products in inventory")
    private Integer totalProducts;
    
    @Schema(description = "Total value of inventory", example = "150000.00")
    private Double totalInventoryValue;
    
    @Schema(description = "Number of products below minimum stock level", example = "5")
    private Integer lowStockCount;
    
    @Data
    @Schema(description = "Details of a product with low stock")
    public static class LowStockItem {
        @Schema(description = "Product ID", example = "1")
        private Long productId;
        
        @Schema(description = "Product name", example = "Wireless Mouse")
        private String productName;
        
        @Schema(description = "Current stock level", example = "5")
        private Integer currentStock;
        
        @Schema(description = "Minimum required stock level", example = "10")
        private Integer minStockLevel;
        
        @Schema(description = "Units needed to reach minimum level", example = "5")
        private Integer unitsNeeded;
        
        @Schema(description = "Last restock date", example = "2025-04-13T14:30:00")
        private String lastRestockDate;
    }
}