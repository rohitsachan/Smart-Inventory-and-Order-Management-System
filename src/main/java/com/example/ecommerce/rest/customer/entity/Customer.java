package com.example.ecommerce.rest.customer.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(description = "Customer entity representing registered users of the system")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the customer", example = "1")
    private Long id;
    
    @Schema(description = "Full name of the customer", example = "John Doe")
    private String name;
    
    @Schema(description = "Email address of the customer", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "Contact phone number", example = "+1-555-123-4567")
    private String phone;
    
    @Schema(description = "Delivery address", example = "123 Main St, City, Country")
    private String address;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Password (will be stored encrypted)", example = "strongPassword123")
    private String password;
    
    @Schema(
        description = "User role for authorization",
        example = "USER",
        allowableValues = {"USER", "ADMIN"}
    )
    private String role = "USER";
}