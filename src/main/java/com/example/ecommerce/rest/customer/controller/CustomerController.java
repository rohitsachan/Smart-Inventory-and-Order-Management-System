package com.example.ecommerce.rest.customer.controller;

import com.example.ecommerce.rest.customer.entity.Customer;
import com.example.ecommerce.rest.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Management", description = "APIs for managing customer accounts")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(
        summary = "Register new customer",
        description = "Creates a new customer account. This endpoint is publicly accessible."
    )
    @ApiResponse(responseCode = "200", description = "Customer registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid customer data or email already exists")
    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(
            @Parameter(description = "Customer registration details", required = true)
            @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }

    @Operation(
        summary = "Get customer profile",
        description = "Retrieves the profile of a specific customer"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @customerSecurity.isCustomerOwner(#id)")
    public ResponseEntity<Customer> getCustomer(
            @Parameter(description = "ID of the customer to retrieve")
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(
        summary = "Update customer profile",
        description = "Updates an existing customer's information"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @customerSecurity.isCustomerOwner(#id)")
    public ResponseEntity<Customer> updateCustomer(
            @Parameter(description = "ID of the customer to update")
            @PathVariable Long id,
            @Parameter(description = "Updated customer details")
            @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @Operation(
        summary = "Delete customer",
        description = "Removes a customer account from the system"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete")
            @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Get all customers",
        description = "Retrieves a list of all customers. Admin access only."
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customers")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}