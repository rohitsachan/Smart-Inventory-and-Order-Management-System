package com.example.ecommerce.rest.monitoring.controller;

import com.example.ecommerce.rest.monitoring.BusinessMetricsCollector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/monitoring")
@RequiredArgsConstructor
@Tag(name = "System Monitoring", description = "APIs for system health and metrics monitoring")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class MonitoringController {
    private final HealthEndpoint healthEndpoint;
    private final MetricsEndpoint metricsEndpoint;
    private final BusinessMetricsCollector metricsCollector;

    @Operation(
        summary = "Get system health",
        description = "Returns detailed health status of the system and its components"
    )
    @ApiResponse(responseCode = "200", description = "Health check successful")
    @GetMapping("/health")
    public ResponseEntity<HealthComponent> getHealth() {
        return ResponseEntity.ok(healthEndpoint.health());
    }

    @Operation(
        summary = "Get system metrics",
        description = "Returns key performance metrics and business metrics"
    )
    @ApiResponse(responseCode = "200", description = "Metrics retrieved successfully")
    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        
        // Collect system metrics
        metrics.put("jvm", metricsEndpoint.metric("jvm.memory.used", null));
        metrics.put("http", metricsEndpoint.metric("http.server.requests", null));
        metrics.put("cpu", metricsEndpoint.metric("system.cpu.usage", null));
        
        // Collect business metrics
        metrics.put("orders", metricsEndpoint.metric("orders.created", null));
        metrics.put("payments", metricsEndpoint.metric("payments.processed", null));
        metrics.put("inventory", metricsEndpoint.metric("inventory.low_stock", null));
        
        return ResponseEntity.ok(metrics);
    }

    @Operation(
        summary = "Get API performance metrics",
        description = "Returns latency and throughput metrics for all API endpoints"
    )
    @ApiResponse(responseCode = "200", description = "API metrics retrieved successfully")
    @GetMapping("/api-metrics")
    public ResponseEntity<Map<String, Object>> getApiMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("latency", metricsEndpoint.metric("api.latency", null));
        metrics.put("requests", metricsEndpoint.metric("http.server.requests", null));
        return ResponseEntity.ok(metrics);
    }
}