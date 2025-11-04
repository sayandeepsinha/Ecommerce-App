package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Cart entity
 * Simple interface - Spring Data JPA generates the implementation
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    /**
     * Find a cart by user ID
     * Each user has only one cart
     */
    Optional<Cart> findByUserId(Long userId);
}
