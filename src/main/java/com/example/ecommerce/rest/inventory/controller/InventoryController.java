package com.example.ecommerce.rest.inventory.controller;

import com.example.ecommerce.rest.inventory.entity.InventoryEvent;
import com.example.ecommerce.rest.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "APIs for managing product inventory")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class InventoryController {
    private final InventoryService inventoryService;
    
    @Operation(
        summary = "Add stock",
        description = "Increases the stock level of a product and records the inventory event"
    )
    @ApiResponse(responseCode = "200", description = "Stock added successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PostMapping("/{productId}/add")
    public ResponseEntity<InventoryEvent> addStock(
            @Parameter(description = "ID of the product to add stock to")
            @PathVariable Long productId,
            @Parameter(description = "Quantity to add", required = true)
            @RequestParam Integer quantity,
            @Parameter(description = "Reason for stock addition")
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(inventoryService.addStock(productId, quantity, reason));
    }
    
    @Operation(
        summary = "Remove stock",
        description = "Decreases the stock level of a product and records the inventory event"
    )
    @ApiResponse(responseCode = "200", description = "Stock removed successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Insufficient stock")
    @PostMapping("/{productId}/remove")
    public ResponseEntity<InventoryEvent> removeStock(
            @Parameter(description = "ID of the product to remove stock from")
            @PathVariable Long productId,
            @Parameter(description = "Quantity to remove", required = true)
            @RequestParam Integer quantity,
            @Parameter(description = "Reason for stock removal")
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(inventoryService.removeStock(productId, quantity, reason));
    }
    
    @Operation(
        summary = "Adjust stock level",
        description = "Sets the stock level to a specific value and records the adjustment"
    )
    @ApiResponse(responseCode = "200", description = "Stock adjusted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PostMapping("/{productId}/adjust")
    public ResponseEntity<InventoryEvent> adjustStock(
            @Parameter(description = "ID of the product to adjust stock for")
            @PathVariable Long productId,
            @Parameter(description = "New stock quantity", required = true)
            @RequestParam Integer newQuantity,
            @Parameter(description = "Reason for adjustment")
            @RequestParam(required = false) String reason) {
        return ResponseEntity.ok(inventoryService.adjustStock(productId, newQuantity, reason));
    }
    
    @Operation(
        summary = "Get inventory history",
        description = "Retrieves the inventory event history for a specific product"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory history")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{productId}/history")
    public ResponseEntity<List<InventoryEvent>> getProductInventoryHistory(
            @Parameter(description = "ID of the product to get history for")
            @PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getProductInventoryHistory(productId));
    }
    
    @Operation(
        summary = "Check low stock levels",
        description = "Checks and reports products with stock levels below their threshold"
    )
    @ApiResponse(responseCode = "200", description = "Stock check completed")
    @GetMapping("/check-low-stock")
    public ResponseEntity<Void> checkLowStockLevels() {
        inventoryService.checkLowStockLevels();
        return ResponseEntity.ok().build();
    }
}