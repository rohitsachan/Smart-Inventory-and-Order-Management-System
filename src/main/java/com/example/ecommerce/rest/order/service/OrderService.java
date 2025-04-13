package com.example.ecommerce.rest.order.service;

import com.example.ecommerce.rest.customer.service.CustomerService;
import com.example.ecommerce.rest.inventory.service.InventoryService;
import com.example.ecommerce.rest.order.entity.Order;
import com.example.ecommerce.rest.order.entity.OrderItem;
import com.example.ecommerce.rest.order.repository.OrderRepository;
import com.example.ecommerce.rest.product.entity.Product;
import com.example.ecommerce.rest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    
    @Transactional
    public Order createOrder(Order order) {
        // Validate customer
        customerService.getCustomerById(order.getCustomer().getId());
        
        // Process each item
        for (OrderItem item : order.getItems()) {
            Product product = productService.getProductById(item.getProduct().getId());
            item.setPrice(product.getPrice());
            item.setOrder(order);
            
            // Check and update inventory
            inventoryService.removeStock(
                product.getId(),
                item.getQuantity(),
                "Order: " + order.getId()
            );
        }
        
        // Calculate total
        double total = order.getItems().stream()
            .mapToDouble(OrderItem::getSubtotal)
            .sum();
        order.setTotalAmount(total);
        
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        
        if (status.equals("CANCELLED")) {
            // Return items to inventory
            for (OrderItem item : order.getItems()) {
                inventoryService.addStock(
                    item.getProduct().getId(),
                    item.getQuantity(),
                    "Order Cancelled: " + orderId
                );
            }
        }
        
        return orderRepository.save(order);
    }
    
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    public List<Order> getCustomerOrders(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}