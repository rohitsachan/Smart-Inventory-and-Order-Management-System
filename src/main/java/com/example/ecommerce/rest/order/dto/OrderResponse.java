package com.example.ecommerce.rest.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Response object containing order details")
public class OrderResponse {
    @Schema(description = "Order ID", example = "1")
    private Long id;
    
    @Schema(description = "Customer ID", example = "1")
    private Long customerId;
    
    @Schema(description = "Customer name", example = "John Doe")
    private String customerName;
    
    @Schema(description = "Order creation date and time", example = "2025-04-13T14:30:00")
    private LocalDateTime orderDate;
    
    @Schema(
        description = "Current status of the order",
        example = "CONFIRMED",
        allowableValues = {"PENDING", "CONFIRMED", "SHIPPED", "DELIVERED", "CANCELLED"}
    )
    private String status;
    
    @Schema(description = "Total order amount", example = "299.99")
    private Double totalAmount;
    
    @Schema(description = "List of items in the order")
    private List<OrderItemResponse> items;
    
    @Schema(description = "Payment information if available")
    private PaymentInfo payment;
    
    @Data
    @Schema(description = "Order item details")
    public static class OrderItemResponse {
        @Schema(description = "Item ID", example = "1")
        private Long id;
        
        @Schema(description = "Product ID", example = "1")
        private Long productId;
        
        @Schema(description = "Product name", example = "Wireless Mouse")
        private String productName;
        
        @Schema(description = "Quantity ordered", example = "2")
        private Integer quantity;
        
        @Schema(description = "Price per unit at time of order", example = "29.99")
        private Double price;
        
        @Schema(description = "Subtotal for this item", example = "59.98")
        private Double subtotal;
    }
    
    @Data
    @Schema(description = "Payment information")
    public static class PaymentInfo {
        @Schema(description = "Payment transaction ID", example = "TXN-2025-04-13-123456")
        private String transactionId;
        
        @Schema(
            description = "Payment status",
            example = "SUCCESS",
            allowableValues = {"SUCCESS", "PENDING", "FAILED"}
        )
        private String status;
        
        @Schema(description = "Payment method used", example = "CREDIT_CARD")
        private String paymentMethod;
        
        @Schema(description = "Payment processing date", example = "2025-04-13T14:30:00")
        private LocalDateTime paymentDate;
    }
}