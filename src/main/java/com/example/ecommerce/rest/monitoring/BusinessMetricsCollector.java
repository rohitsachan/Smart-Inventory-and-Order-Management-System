package com.example.ecommerce.rest.monitoring;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusinessMetricsCollector {
    private final MeterRegistry registry;
    
    public void recordOrderCreated(Double amount) {
        registry.counter("orders.created").increment();
        registry.gauge("orders.amount", amount);
    }
    
    public void recordPaymentProcessed(String status) {
        registry.counter("payments.processed." + status.toLowerCase()).increment();
    }
    
    public void recordLowStockEvent(String productName) {
        registry.counter("inventory.low_stock").increment();
    }
    
    public void recordProductViewed(Long productId) {
        registry.counter("products.viewed." + productId).increment();
    }
    
    public void recordApiLatency(String endpoint, long milliseconds) {
        registry.timer("api.latency", "endpoint", endpoint)
               .record(java.time.Duration.ofMillis(milliseconds));
    }
}