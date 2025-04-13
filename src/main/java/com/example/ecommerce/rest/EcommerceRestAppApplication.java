package com.example.ecommerce.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EcommerceRestAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceRestAppApplication.class, args);
    }
}
