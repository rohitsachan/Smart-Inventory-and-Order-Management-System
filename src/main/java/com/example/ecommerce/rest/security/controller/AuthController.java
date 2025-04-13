package com.example.ecommerce.rest.security.controller;

import com.example.ecommerce.rest.customer.entity.Customer;
import com.example.ecommerce.rest.customer.service.CustomerService;
import com.example.ecommerce.rest.security.dto.LoginRequest;
import com.example.ecommerce.rest.security.dto.LoginResponse;
import com.example.ecommerce.rest.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API endpoints")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final CustomerService customerService;
    
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user with email and password, returns JWT token and user details"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Authentication successful",
        content = @Content(schema = @Schema(implementation = LoginResponse.class))
    )
    @ApiResponse(
        responseCode = "401",
        description = "Authentication failed",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );
        
        String token = tokenProvider.generateToken(authentication);
        Customer customer = customerService.findByEmail(loginRequest.getEmail());
        
        return ResponseEntity.ok(new LoginResponse(token, customer));
    }
    
    @Operation(
        summary = "Validate JWT token",
        description = "Validates a JWT token and returns the associated user information"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Token is valid",
        content = @Content(schema = @Schema(implementation = Customer.class))
    )
    @ApiResponse(
        responseCode = "401",
        description = "Token is invalid",
        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
    )
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        boolean isValid = tokenProvider.validateToken(token);
        if (isValid) {
            String email = tokenProvider.getUsernameFromToken(token);
            Customer customer = customerService.findByEmail(email);
            return ResponseEntity.ok(customer);
        }
        
        return ResponseEntity.status(401).body("Invalid token");
    }
}