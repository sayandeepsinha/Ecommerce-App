package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart entity - represents a user's shopping cart
 * Each user has ONE cart with multiple cart items
 */
@Entity
@Table(name = "carts")
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
    private List<CartItem> items = new ArrayList<>();

    public Cart() {}

    public Cart(Long id, User user, List<CartItem> items) {
        this.id = id;
        this.user = user;
        this.items = items != null ? items : new ArrayList<>();
    }

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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public static CartBuilder builder() {
        return new CartBuilder();
    }

    public static class CartBuilder {
        private Long id;
        private User user;
        private List<CartItem> items;

        public CartBuilder id(Long id) { this.id = id; return this; }
        public CartBuilder user(User user) { this.user = user; return this; }
        public CartBuilder items(List<CartItem> items) { this.items = items; return this; }

        public Cart build() {
            return new Cart(id, user, items);
        }
    }
}
