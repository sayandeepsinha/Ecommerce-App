package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * CartItem entity - represents a single product in a cart with quantity
 * Links Cart and Product together
 */
@Entity
@Table(name = "cart_items")
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

    public CartItem() {}

    public CartItem(Long id, Cart cart, Product product, Integer quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Calculate subtotal for this cart item (price * quantity)
     */
    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public static CartItemBuilder builder() {
        return new CartItemBuilder();
    }

    public static class CartItemBuilder {
        private Long id;
        private Cart cart;
        private Product product;
        private Integer quantity;

        public CartItemBuilder id(Long id) { this.id = id; return this; }
        public CartItemBuilder cart(Cart cart) { this.cart = cart; return this; }
        public CartItemBuilder product(Product product) { this.product = product; return this; }
        public CartItemBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }

        public CartItem build() {
            return new CartItem(id, cart, product, quantity);
        }
    }
}
