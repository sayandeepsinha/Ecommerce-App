package com.example.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for cart
 * Contains all cart items and the total
 */
public class CartResponse {
	private Long id;
	private List<CartItemResponse> items;
	private BigDecimal total; // sum of all item subtotals
	private Integer itemCount; // total number of items

    public CartResponse() {}

    public CartResponse(Long id, List<CartItemResponse> items, BigDecimal total, Integer itemCount) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.itemCount = itemCount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public List<CartItemResponse> getItems() { return items; }
    public void setItems(List<CartItemResponse> items) { this.items = items; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public Integer getItemCount() { return itemCount; }
    public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }

    public static CartResponseBuilder builder() {
        return new CartResponseBuilder();
    }

    public static class CartResponseBuilder {
        private Long id;
        private List<CartItemResponse> items;
        private BigDecimal total;
        private Integer itemCount;

        public CartResponseBuilder id(Long id) { this.id = id; return this; }
        public CartResponseBuilder items(List<CartItemResponse> items) { this.items = items; return this; }
        public CartResponseBuilder total(BigDecimal total) { this.total = total; return this; }
        public CartResponseBuilder itemCount(Integer itemCount) { this.itemCount = itemCount; return this; }

        public CartResponse build() {
            return new CartResponse(id, items, total, itemCount);
        }
    }
}
