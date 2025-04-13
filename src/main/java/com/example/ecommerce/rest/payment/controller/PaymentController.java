package com.example.ecommerce.rest.payment.controller;

import com.example.ecommerce.rest.payment.entity.Payment;
import com.example.ecommerce.rest.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for processing payments")
@SecurityRequirement(name = "bearerAuth")
public class PaymentController {
    private final PaymentService paymentService;
    
    @Operation(
        summary = "Process payment for order",
        description = "Processes a payment for a given order using the specified payment method"
    )
    @ApiResponse(responseCode = "200", description = "Payment processed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid payment data")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PostMapping("/orders/{orderId}")
    public ResponseEntity<Payment> processPayment(
            @Parameter(description = "ID of the order to process payment for")
            @PathVariable Long orderId,
            @Parameter(description = "Payment method (e.g., CREDIT_CARD, DEBIT_CARD)")
            @RequestParam String paymentMethod) {
        return ResponseEntity.ok(paymentService.processPayment(orderId, paymentMethod));
    }
    
    @Operation(
        summary = "Get payment by order ID",
        description = "Retrieves payment information for a specific order"
    )
    @ApiResponse(responseCode = "200", description = "Payment found")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Payment> getPaymentByOrderId(
            @Parameter(description = "ID of the order to get payment for")
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }
    
    @Operation(
        summary = "Get payment by transaction ID",
        description = "Retrieves payment information using a transaction ID"
    )
    @ApiResponse(responseCode = "200", description = "Payment found")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    @GetMapping("/transactions/{transactionId}")
    public ResponseEntity<Payment> getPaymentByTransactionId(
            @Parameter(description = "Transaction ID of the payment")
            @PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.getPaymentByTransactionId(transactionId));
    }
    
    @Operation(
        summary = "Retry failed payment",
        description = "Attempts to retry a previously failed payment for an order"
    )
    @ApiResponse(responseCode = "200", description = "Payment retried successfully")
    @ApiResponse(responseCode = "400", description = "Payment already successful or invalid state")
    @ApiResponse(responseCode = "404", description = "Payment not found")
    @PostMapping("/orders/{orderId}/retry")
    public ResponseEntity<Payment> retryPayment(
            @Parameter(description = "ID of the order to retry payment for")
            @PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.retryPayment(orderId));
    }
}