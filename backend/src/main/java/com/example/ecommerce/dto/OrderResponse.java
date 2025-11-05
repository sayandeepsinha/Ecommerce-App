package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    @SuppressWarnings("unused")
	private Long id;
    @SuppressWarnings("unused")
	private Long userId;
    @SuppressWarnings("unused")
	private List<OrderItemResponse> items;
    @SuppressWarnings("unused")
	private BigDecimal total;
    @SuppressWarnings("unused")
	private String status;
    @SuppressWarnings("unused")
	private LocalDateTime createdAt;
    @SuppressWarnings("unused")
	private String shippingAddress;
}
