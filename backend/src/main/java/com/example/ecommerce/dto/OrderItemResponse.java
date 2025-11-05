package com.example.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Response DTO for a single order item
 */
public class OrderItemResponse {
	private Long id;
	private Long productId;
	private String productName;
	private BigDecimal price;
	private Integer quantity;
	private BigDecimal subtotal;

    public OrderItemResponse() {}

    public OrderItemResponse(Long id, Long productId, String productName, 
                            BigDecimal price, Integer quantity, BigDecimal subtotal) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public static OrderItemResponseBuilder builder() {
        return new OrderItemResponseBuilder();
    }

    public static class OrderItemResponseBuilder {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subtotal;

        public OrderItemResponseBuilder id(Long id) { this.id = id; return this; }
        public OrderItemResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public OrderItemResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public OrderItemResponseBuilder price(BigDecimal price) { this.price = price; return this; }
        public OrderItemResponseBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public OrderItemResponseBuilder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }

        public OrderItemResponse build() {
            return new OrderItemResponse(id, productId, productName, price, quantity, subtotal);
        }
    }
}
