package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for CartItem entity
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Find a cart item by cart ID and product ID
     * Used to check if a product is already in the cart
     */
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
