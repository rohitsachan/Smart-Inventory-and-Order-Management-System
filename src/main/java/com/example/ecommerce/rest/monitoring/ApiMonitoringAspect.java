package com.example.ecommerce.rest.monitoring;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiMonitoringAspect {
    private final BusinessMetricsCollector metricsCollector;
    
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object monitorApiEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            String endpoint = joinPoint.getSignature().getDeclaringTypeName() + 
                "." + joinPoint.getSignature().getName();
            metricsCollector.recordApiLatency(endpoint, duration);
        }
    }
}