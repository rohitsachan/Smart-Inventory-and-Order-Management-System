package com.example.ecommerce.rest.inventory.service;

import com.example.ecommerce.rest.inventory.entity.InventoryEvent;
import com.example.ecommerce.rest.inventory.repository.InventoryEventRepository;
import com.example.ecommerce.rest.product.entity.Product;
import com.example.ecommerce.rest.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryEventRepository inventoryEventRepository;
    private final ProductService productService;
    
    @Transactional
    public InventoryEvent addStock(Long productId, Integer quantity, String reason) {
        Product product = productService.getProductById(productId);
        product.setStockLevel(product.getStockLevel() + quantity);
        productService.updateProduct(productId, product);
        
        InventoryEvent event = new InventoryEvent();
        event.setProductId(productId);
        event.setQuantityChange(quantity);
        event.setEventType("STOCK_IN");
        event.setTimestamp(LocalDateTime.now());
        event.setReason(reason);
        
        return inventoryEventRepository.save(event);
    }
    
    @Transactional
    public InventoryEvent removeStock(Long productId, Integer quantity, String reason) {
        Product product = productService.getProductById(productId);
        if (product.getStockLevel() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        product.setStockLevel(product.getStockLevel() - quantity);
        productService.updateProduct(productId, product);
        
        InventoryEvent event = new InventoryEvent();
        event.setProductId(productId);
        event.setQuantityChange(-quantity);
        event.setEventType("STOCK_OUT");
        event.setTimestamp(LocalDateTime.now());
        event.setReason(reason);
        
        return inventoryEventRepository.save(event);
    }
    
    public List<InventoryEvent> getProductInventoryHistory(Long productId) {
        return inventoryEventRepository.findByProductId(productId);
    }
    
    @Transactional
    public InventoryEvent adjustStock(Long productId, Integer newQuantity, String reason) {
        Product product = productService.getProductById(productId);
        int adjustment = newQuantity - product.getStockLevel();
        
        product.setStockLevel(newQuantity);
        productService.updateProduct(productId, product);
        
        InventoryEvent event = new InventoryEvent();
        event.setProductId(productId);
        event.setQuantityChange(adjustment);
        event.setEventType("ADJUSTMENT");
        event.setTimestamp(LocalDateTime.now());
        event.setReason(reason);
        
        return inventoryEventRepository.save(event);
    }
    
    public void checkLowStockLevels() {
        List<Product> lowStockProducts = productService.getLowStockProducts();
        // Here we would typically integrate with a notification service
        // to alert about low stock levels
    }
}