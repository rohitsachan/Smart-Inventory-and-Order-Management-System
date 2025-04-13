package com.example.ecommerce.rest.notification.service;

import com.example.ecommerce.rest.customer.entity.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    
    public void sendOrderConfirmation(Customer customer, String orderId) {
        // Simulate email notification
        log.info("Sending order confirmation email to: {} for order: {}", 
            customer.getEmail(), orderId);
        
        // Simulate SMS notification
        if (customer.getPhone() != null) {
            log.info("Sending order confirmation SMS to: {} for order: {}", 
                customer.getPhone(), orderId);
        }
    }
    
    public void sendLowStockAlert(String productName, Integer currentStock) {
        // Simulate admin notification
        log.warn("LOW STOCK ALERT: Product '{}' has low stock level: {}", 
            productName, currentStock);
    }
    
    public void sendOrderStatusUpdate(Customer customer, String orderId, String status) {
        // Simulate email notification
        log.info("Sending order status update email to: {}. Order: {} is now {}", 
            customer.getEmail(), orderId, status);
        
        // Simulate SMS notification
        if (customer.getPhone() != null) {
            log.info("Sending order status update SMS to: {}. Order: {} is now {}", 
                customer.getPhone(), orderId, status);
        }
    }
    
    public void sendOrderShippingUpdate(Customer customer, String orderId, String trackingInfo) {
        // Simulate email notification
        log.info("Sending shipping update email to: {}. Order: {} - Tracking: {}", 
            customer.getEmail(), orderId, trackingInfo);
        
        // Simulate SMS notification
        if (customer.getPhone() != null) {
            log.info("Sending shipping update SMS to: {}. Order: {} - Tracking: {}", 
                customer.getPhone(), orderId, trackingInfo);
        }
    }
}