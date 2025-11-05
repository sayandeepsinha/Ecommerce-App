package com.example.ecommerce.model;

import jakarta.persistence.*;

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
    private String status = "pending";

    /**
     * When the order was created
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Shipping address
     */
    @SuppressWarnings("unused")
	private String shippingAddress;

    public Order() {}

    public Order(Long id, Long userId, List<OrderItem> items, BigDecimal total, 
                String status, LocalDateTime createdAt, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.items = items != null ? items : new ArrayList<>();
        this.total = total;
        this.status = status != null ? status : "pending";
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.shippingAddress = shippingAddress;
    }

    /**
     * Helper method to add an item to the order
     */
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    public static class OrderBuilder {
        private Long id;
        private Long userId;
        private List<OrderItem> items;
        private BigDecimal total;
        private String status;
        private LocalDateTime createdAt;
        private String shippingAddress;

        public OrderBuilder id(Long id) { this.id = id; return this; }
        public OrderBuilder userId(Long userId) { this.userId = userId; return this; }
        public OrderBuilder items(List<OrderItem> items) { this.items = items; return this; }
        public OrderBuilder total(BigDecimal total) { this.total = total; return this; }
        public OrderBuilder status(String status) { this.status = status; return this; }
        public OrderBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderBuilder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }

        public Order build() {
            return new Order(id, userId, items, total, status, createdAt, shippingAddress);
        }
    }
}
