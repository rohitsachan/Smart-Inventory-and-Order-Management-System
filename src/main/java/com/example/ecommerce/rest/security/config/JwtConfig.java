package com.example.ecommerce.rest.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secret = "defaultSecretKey123!@#defaultSecretKey123!@#defaultSecretKey123!@#";
    private long expirationTime = 864000000; // 10 days
}