package com.example.ecommerce.rest.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login request payload")
public class LoginRequest {
    @Schema(
        description = "User's email address",
        example = "john.doe@example.com",
        required = true
    )
    private String email;
    
    @Schema(
        description = "User's password",
        example = "strongPassword123",
        required = true
    )
    private String password;
}