package com.example.ecommerce.rest.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Request object for processing payments")
public class PaymentProcessRequest {
    @NotNull(message = "Order ID is required")
    @Schema(description = "ID of the order to process payment for", example = "1", required = true)
    private Long orderId;
    
    @NotBlank(message = "Payment method is required")
    @Schema(
        description = "Payment method to use",
        example = "CREDIT_CARD",
        allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "BANK_TRANSFER", "DIGITAL_WALLET"},
        required = true
    )
    private String paymentMethod;
    
    @Schema(description = "Payment method details (encrypted)", example = "encrypted_card_details")
    private String paymentDetails;
    
    @Schema(
        description = "Currency for the transaction",
        example = "USD",
        defaultValue = "USD"
    )
    private String currency = "USD";
    
    @Schema(
        description = "Save payment method for future use",
        example = "false",
        defaultValue = "false"
    )
    private boolean savePaymentMethod = false;
}