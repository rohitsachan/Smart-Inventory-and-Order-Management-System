package com.example.ecommerce.rest.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Notification object for system events and alerts")
public class NotificationDTO {
    @Schema(description = "Unique identifier of the notification", example = "1")
    private Long id;
    
    @Schema(
        description = "Type of notification",
        example = "LOW_STOCK",
        allowableValues = {
            "LOW_STOCK",
            "ORDER_STATUS",
            "PAYMENT_STATUS",
            "SYSTEM_ALERT"
        }
    )
    private String type;
    
    @Schema(description = "Priority level of the notification", 
        example = "HIGH",
        allowableValues = {"LOW", "MEDIUM", "HIGH"}
    )
    private String priority;
    
    @Schema(description = "Main notification message", 
        example = "Product 'Wireless Mouse' has reached low stock level (5 units remaining)"
    )
    private String message;
    
    @Schema(description = "Additional details or context", 
        example = "Minimum stock level: 10, Current stock: 5, Product ID: 123"
    )
    private String details;
    
    @Schema(description = "Target user or role for the notification", 
        example = "ADMIN"
    )
    private String target;
    
    @Schema(description = "Whether the notification has been read", 
        example = "false"
    )
    private boolean read = false;
    
    @Schema(description = "When the notification was created", 
        example = "2025-04-13T14:30:00"
    )
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Schema(description = "When the notification was read (if applicable)", 
        example = "2025-04-13T14:35:00"
    )
    private LocalDateTime readAt;
    
    @Schema(description = "Reference ID (e.g., Order ID, Product ID) related to this notification",
        example = "123"
    )
    private String referenceId;
    
    @Schema(description = "Action URL or endpoint related to this notification",
        example = "/api/v1/inventory/products/123"
    )
    private String actionUrl;
}