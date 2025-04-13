package com.example.ecommerce.rest.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Standard error response for API errors")
public class ErrorResponse {
    @Schema(
        description = "HTTP status code",
        example = "400",
        required = true
    )
    private int status;
    
    @Schema(
        description = "Error message",
        example = "Invalid input: email is required",
        required = true
    )
    private String message;
    
    @Schema(
        description = "Detailed error description",
        example = "The request validation failed due to missing required field: email"
    )
    private String details;
    
    @Schema(
        description = "Timestamp when the error occurred",
        example = "2025-04-13T14:30:00",
        required = true
    )
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Schema(
        description = "Path where the error occurred",
        example = "/api/v1/customers/register",
        required = true
    )
    private String path;
}