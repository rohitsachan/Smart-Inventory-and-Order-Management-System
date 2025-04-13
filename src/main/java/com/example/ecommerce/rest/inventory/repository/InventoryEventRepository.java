package com.example.ecommerce.rest.inventory.repository;

import com.example.ecommerce.rest.inventory.entity.InventoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryEventRepository extends JpaRepository<InventoryEvent, Long> {
    List<InventoryEvent> findByProductId(Long productId);
    List<InventoryEvent> findByProductIdAndEventType(Long productId, String eventType);
}