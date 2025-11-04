package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart entity - represents a user's shopping cart
 * Each user has ONE cart with multiple cart items
 */
@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with User
     * Each user has exactly one cart
     */
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    /**
     * One-to-many relationship with CartItems
     * A cart can have many items
     * CASCADE.ALL means when we delete a cart, all its items are deleted too
     * orphanRemoval = true means if we remove an item from the list, it's deleted from DB
     */
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CartItem> items = new ArrayList<>();

    /**
     * Helper method to add an item to the cart
     */
    public void addItem(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    /**
     * Helper method to remove an item from the cart
     */
    public void removeItem(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
