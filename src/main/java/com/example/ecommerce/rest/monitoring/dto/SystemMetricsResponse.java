package com.example.ecommerce.rest.monitoring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Map;

@Data
@Schema(description = "Response object containing system metrics and performance data")
public class SystemMetricsResponse {
    @Schema(description = "JVM metrics including memory usage and threads")
    private JvmMetrics jvm;
    
    @Schema(description = "HTTP request metrics")
    private HttpMetrics http;
    
    @Schema(description = "Business metrics related to orders and inventory")
    private BusinessMetrics business;
    
    @Schema(description = "System resource usage metrics")
    private SystemMetrics system;
    
    @Data
    @Schema(description = "JVM-related metrics")
    public static class JvmMetrics {
        @Schema(description = "Used heap memory in MB", example = "512.5")
        private Double heapUsed;
        
        @Schema(description = "Maximum heap memory in MB", example = "1024.0")
        private Double heapMax;
        
        @Schema(description = "Active threads count", example = "25")
        private Integer threadCount;
        
        @Schema(description = "Garbage collection statistics")
        private Map<String, Double> gcStats;
    }
    
    @Data
    @Schema(description = "HTTP request metrics")
    public static class HttpMetrics {
        @Schema(description = "Total requests processed", example = "15000")
        private Long totalRequests;
        
        @Schema(description = "Average response time in ms", example = "45.7")
        private Double avgResponseTime;
        
        @Schema(description = "Request count by status code")
        private Map<String, Long> statusCodes;
        
        @Schema(description = "Top 10 endpoints by request count")
        private Map<String, Long> topEndpoints;
    }
    
    @Data
    @Schema(description = "Business-specific metrics")
    public static class BusinessMetrics {
        @Schema(description = "Orders processed today", example = "156")
        private Long dailyOrders;
        
        @Schema(description = "Total sales value today", example = "15679.99")
        private Double dailySales;
        
        @Schema(description = "Products with low stock count", example = "5")
        private Integer lowStockCount;
        
        @Schema(description = "Active user sessions", example = "234")
        private Integer activeSessions;
    }
    
    @Data
    @Schema(description = "System resource metrics")
    public static class SystemMetrics {
        @Schema(description = "CPU usage percentage", example = "45.5")
        private Double cpuUsage;
        
        @Schema(description = "System memory usage percentage", example = "78.3")
        private Double memoryUsage;
        
        @Schema(description = "Disk usage percentage", example = "65.4")
        private Double diskUsage;
        
        @Schema(description = "System load average", example = "1.25")
        private Double systemLoad;
    }
}