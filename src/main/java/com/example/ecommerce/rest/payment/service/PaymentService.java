package com.example.ecommerce.rest.payment.service;

import com.example.ecommerce.rest.notification.service.NotificationService;
import com.example.ecommerce.rest.order.entity.Order;
import com.example.ecommerce.rest.order.service.OrderService;
import com.example.ecommerce.rest.payment.entity.Payment;
import com.example.ecommerce.rest.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final NotificationService notificationService;
    private final Random random = new Random();
    
    @Transactional
    public Payment processPayment(Long orderId, String paymentMethod) {
        Order order = orderService.getOrderById(orderId);
        
        // Create payment record
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());
        payment.setTransactionId(UUID.randomUUID().toString());
        
        // Simulate payment processing with 90% success rate
        if (random.nextInt(10) < 9) {
            payment.setStatus("SUCCESS");
            orderService.updateOrderStatus(orderId, "CONFIRMED");
            notificationService.sendOrderConfirmation(order.getCustomer(), orderId.toString());
        } else {
            payment.setStatus("FAILED");
            payment.setFailureReason("Payment declined by bank");
            orderService.updateOrderStatus(orderId, "PAYMENT_FAILED");
        }
        
        return paymentRepository.save(payment);
    }
    
    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    public Payment getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    }
    
    @Transactional
    public Payment retryPayment(Long orderId) {
        Payment failedPayment = getPaymentByOrderId(orderId);
        if ("SUCCESS".equals(failedPayment.getStatus())) {
            throw new RuntimeException("Payment already successful");
        }
        
        return processPayment(orderId, failedPayment.getPaymentMethod());
    }
}