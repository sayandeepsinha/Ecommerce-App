package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * OrderItem entity - represents a product in an order
 * Stores a snapshot of product info at the time of order
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with Order
     * Many order items belong to one order
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Product ID - we store the ID instead of a relationship
     * This way if the product is deleted later, we still have the order history
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * Product name at time of order
     * Stored as a snapshot in case product name changes later
     */
    @Column(nullable = false)
    private String productName;

    /**
     * Price at time of order
     * Stored as a snapshot in case product price changes later
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Quantity ordered
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Helper method to calculate subtotal
     */
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}
