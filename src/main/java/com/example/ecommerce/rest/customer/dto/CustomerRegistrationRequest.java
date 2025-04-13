package com.example.ecommerce.rest.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for customer registration")
public class CustomerRegistrationRequest {
    @NotBlank(message = "Name is required")
    @Schema(description = "Full name of the customer", example = "John Doe", required = true)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address (will be used for login)", example = "john.doe@example.com", required = true)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
        message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character"
    )
    @Schema(
        description = "Password (must be at least 8 characters and contain digits, lowercase, uppercase, and special characters)",
        example = "StrongP@ss123",
        required = true
    )
    private String password;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be in international format")
    @Schema(description = "Contact phone number in international format", example = "+1-555-123-4567", required = true)
    private String phone;
    
    @NotBlank(message = "Address is required")
    @Schema(description = "Delivery address", example = "123 Main St, City, Country", required = true)
    private String address;
}