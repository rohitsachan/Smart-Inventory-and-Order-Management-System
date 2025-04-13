package com.example.ecommerce.rest.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "Response object containing payment processing results")
public class PaymentResponse {
    @Schema(description = "Unique transaction ID", example = "TXN-2025-04-13-123456")
    private String transactionId;
    
    @Schema(description = "ID of the order this payment is for", example = "1")
    private Long orderId;
    
    @Schema(
        description = "Status of the payment",
        example = "SUCCESS",
        allowableValues = {"SUCCESS", "PENDING", "FAILED", "REFUNDED"}
    )
    private String status;
    
    @Schema(description = "Amount processed", example = "299.99")
    private Double amount;
    
    @Schema(description = "Currency of the transaction", example = "USD")
    private String currency;
    
    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    private String paymentMethod;
    
    @Schema(description = "Last 4 digits of card if applicable", example = "1234")
    private String last4;
    
    @Schema(description = "When the payment was processed", example = "2025-04-13T14:30:00")
    private LocalDateTime processedAt;
    
    @Schema(description = "Payment gateway response code", example = "00")
    private String responseCode;
    
    @Schema(description = "Payment gateway response message", example = "Transaction approved")
    private String responseMessage;
    
    @Schema(description = "If payment failed, detailed error message", example = "Insufficient funds")
    private String errorDetails;
    
    @Schema(
        description = "If the payment method was saved for future use",
        example = "true"
    )
    private boolean paymentMethodSaved;
    
    @Schema(description = "Token for saved payment method if applicable", example = "pm_token_123")
    private String savedPaymentMethodToken;
}