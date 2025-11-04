package com.example.ecommerce.repository;

import com.example.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Order entity
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find all orders for a specific user
     * Ordered by creation date (newest first)
     */
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
}
