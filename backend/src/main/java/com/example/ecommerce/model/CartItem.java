package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * CartItem entity - represents a single product in a cart with quantity
 * Links Cart and Product together
 */
@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Many-to-one relationship with Cart
     * Many cart items belong to one cart
     */
    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    /**
     * Many-to-one relationship with Product
     * Many cart items can reference the same product
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * How many of this product the user wants
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Helper method to calculate subtotal for this item
     * price * quantity
     */
    public java.math.BigDecimal getSubtotal() {
        return product.getPrice().multiply(java.math.BigDecimal.valueOf(quantity));
    }
}
