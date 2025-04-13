package com.example.ecommerce.rest.payment.entity;

import com.example.ecommerce.rest.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Schema(description = "Payment entity representing payment transactions for orders")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the payment", example = "1")
    private Long id;
    
    @OneToOne
    @Schema(description = "Order associated with this payment")
    private Order order;
    
    @Schema(
        description = "Payment method used", 
        example = "CREDIT_CARD",
        allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "BANK_TRANSFER", "DIGITAL_WALLET"}
    )
    private String paymentMethod;
    
    @Schema(description = "Unique transaction ID for the payment", example = "TXN-2025-04-13-123456")
    private String transactionId;
    
    @Schema(description = "Amount processed in the payment", example = "299.99")
    private Double amount;
    
    @Schema(
        description = "Current status of the payment", 
        example = "SUCCESS",
        allowableValues = {"SUCCESS", "FAILED", "PENDING"}
    )
    private String status;
    
    @Schema(description = "Timestamp of the payment transaction", example = "2025-04-13T14:30:00")
    private LocalDateTime timestamp = LocalDateTime.now();
    
    @Schema(description = "Reason for payment failure, if applicable", example = "Insufficient funds")
    private String failureReason;
}