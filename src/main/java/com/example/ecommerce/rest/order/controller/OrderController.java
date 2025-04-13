package com.example.ecommerce.rest.order.controller;

import com.example.ecommerce.rest.order.entity.Order;
import com.example.ecommerce.rest.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "APIs for managing orders")
@SecurityRequirement(name = "bearerAuth")
public class OrderController {
    private final OrderService orderService;
    
    @Operation(
        summary = "Create new order",
        description = "Places a new order in the system and updates inventory accordingly"
    )
    @ApiResponse(responseCode = "200", description = "Order created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid order data or insufficient stock")
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @Parameter(description = "Order details including items") 
            @RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }
    
    @Operation(
        summary = "Get order by ID",
        description = "Retrieves details of a specific order"
    )
    @ApiResponse(responseCode = "200", description = "Order found")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @Parameter(description = "ID of the order to retrieve")
            @PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    
    @Operation(
        summary = "Get customer orders",
        description = "Retrieves all orders for a specific customer"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(
            @Parameter(description = "ID of the customer")
            @PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getCustomerOrders(customerId));
    }
    
    @Operation(
        summary = "Get orders by status",
        description = "Retrieves all orders with a specific status"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(
            @Parameter(description = "Status to filter by (e.g., PENDING, CONFIRMED, SHIPPED)")
            @PathVariable String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
    
    @Operation(
        summary = "Update order status",
        description = "Updates the status of an existing order"
    )
    @ApiResponse(responseCode = "200", description = "Order status updated successfully")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @Parameter(description = "ID of the order to update")
            @PathVariable Long id,
            @Parameter(description = "New status value")
            @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }
    
    @Operation(
        summary = "Get all orders",
        description = "Retrieves all orders in the system"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved orders")
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}