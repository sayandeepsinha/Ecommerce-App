package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * OrderItem entity - represents a product snapshot in an order
 * Stores product information at the time of order (price, name) to preserve order history
 */
@Entity
@Table(name = "order_items")
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

    public OrderItem() {}

    public OrderItem(Long id, Order order, Long productId, String productName, 
                    BigDecimal price, Integer quantity) {
        this.id = id;
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Helper method to calculate subtotal
     */
    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    public static class OrderItemBuilder {
        private Long id;
        private Order order;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;

        public OrderItemBuilder id(Long id) { this.id = id; return this; }
        public OrderItemBuilder order(Order order) { this.order = order; return this; }
        public OrderItemBuilder productId(Long productId) { this.productId = productId; return this; }
        public OrderItemBuilder productName(String productName) { this.productName = productName; return this; }
        public OrderItemBuilder price(BigDecimal price) { this.price = price; return this; }
        public OrderItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }

        public OrderItem build() {
            return new OrderItem(id, order, productId, productName, price, quantity);
        }
    }
}
