package com.example.ecommerce.dto;

import java.math.BigDecimal;

/**
 * Response DTO for a single cart item
 */
public class CartItemResponse {
    private Long id;
    private Long productId;
    private String productName;
	private BigDecimal price;
	private String imageUrl;
	private Integer quantity;
	private BigDecimal subtotal; // price * quantity

    public CartItemResponse() {}

    public CartItemResponse(Long id, Long productId, String productName, BigDecimal price, 
                           String imageUrl, Integer quantity, BigDecimal subtotal) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public static CartItemResponseBuilder builder() {
        return new CartItemResponseBuilder();
    }

    public static class CartItemResponseBuilder {
        private Long id;
        private Long productId;
        private String productName;
        private BigDecimal price;
        private String imageUrl;
        private Integer quantity;
        private BigDecimal subtotal;

        public CartItemResponseBuilder id(Long id) { this.id = id; return this; }
        public CartItemResponseBuilder productId(Long productId) { this.productId = productId; return this; }
        public CartItemResponseBuilder productName(String productName) { this.productName = productName; return this; }
        public CartItemResponseBuilder price(BigDecimal price) { this.price = price; return this; }
        public CartItemResponseBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public CartItemResponseBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public CartItemResponseBuilder subtotal(BigDecimal subtotal) { this.subtotal = subtotal; return this; }

        public CartItemResponse build() {
            return new CartItemResponse(id, productId, productName, price, imageUrl, quantity, subtotal);
        }
    }
}
