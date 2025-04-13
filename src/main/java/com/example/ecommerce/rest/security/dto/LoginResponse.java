package com.example.ecommerce.rest.security.dto;

import com.example.ecommerce.rest.customer.entity.Customer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Login response containing authentication token and user details")
public class LoginResponse {
    @Schema(
        description = "JWT authentication token",
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        required = true
    )
    private String token;
    
    @Schema(
        description = "Customer details",
        required = true
    )
    private Customer customer;
}