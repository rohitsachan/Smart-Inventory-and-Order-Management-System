package com.example.ecommerce.rest.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "Request object for updating customer profile information")
public class CustomerProfileUpdateRequest {
    @Schema(description = "Updated full name of the customer", example = "John Doe")
    private String name;
    
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in international format")
    @Schema(description = "Updated contact phone number in international format", example = "+1-555-123-4567")
    private String phone;
    
    @Schema(description = "Updated delivery address", example = "123 Main St, City, Country")
    private String address;
    
    @Schema(
        description = "Current password (required for security-sensitive changes)",
        example = "CurrentP@ss123"
    )
    private String currentPassword;
    
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
        message = "New password must contain at least one digit, one lowercase, one uppercase, and one special character"
    )
    @Schema(
        description = "New password (if changing password)",
        example = "NewStrongP@ss123"
    )
    private String newPassword;
}