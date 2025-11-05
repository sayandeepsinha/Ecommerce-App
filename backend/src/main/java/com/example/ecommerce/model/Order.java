package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Order entity - represents a customer order
 * Created when user completes checkout
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the user who placed the order
     * We store the user ID instead of a direct relationship for simplicity
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * One-to-many relationship with OrderItems
     * An order contains multiple products
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    /**
     * Total price of the order
     */
    @Column(nullable = false)
    private BigDecimal total;

    /**
     * Order status: "pending", "confirmed", "shipped", "delivered", "cancelled"
     * Default is "pending"
     */
    @Column(nullable = false)
    @Builder.Default
    private String status = "pending";

    /**
     * When the order was created
     */
    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Shipping address
     */
    @SuppressWarnings("unused")
	private String shippingAddress;

    /**
     * Helper method to add an item to the order
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}
