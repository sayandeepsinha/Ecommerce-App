package com.example.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for order
 */
public class OrderResponse {
	private Long id;
	private Long userId;
	private List<OrderItemResponse> items;
	private BigDecimal total;
	private String status;
	private LocalDateTime createdAt;
	private String shippingAddress;

    public OrderResponse() {}

    public OrderResponse(Long id, Long userId, List<OrderItemResponse> items, BigDecimal total,
                        String status, LocalDateTime createdAt, String shippingAddress) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
        this.shippingAddress = shippingAddress;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public static OrderResponseBuilder builder() {
        return new OrderResponseBuilder();
    }

    public static class OrderResponseBuilder {
        private Long id;
        private Long userId;
        private List<OrderItemResponse> items;
        private BigDecimal total;
        private String status;
        private LocalDateTime createdAt;
        private String shippingAddress;

        public OrderResponseBuilder id(Long id) { this.id = id; return this; }
        public OrderResponseBuilder userId(Long userId) { this.userId = userId; return this; }
        public OrderResponseBuilder items(List<OrderItemResponse> items) { this.items = items; return this; }
        public OrderResponseBuilder total(BigDecimal total) { this.total = total; return this; }
        public OrderResponseBuilder status(String status) { this.status = status; return this; }
        public OrderResponseBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public OrderResponseBuilder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }

        public OrderResponse build() {
            return new OrderResponse(id, userId, items, total, status, createdAt, shippingAddress);
        }
    }
}
