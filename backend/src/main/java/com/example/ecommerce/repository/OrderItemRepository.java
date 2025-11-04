package com.example.ecommerce.repository;

import com.example.ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for OrderItem entity
 * Basic CRUD operations inherited from JpaRepository
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // No custom methods needed for now
}
