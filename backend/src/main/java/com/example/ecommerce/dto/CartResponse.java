package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for cart
 * Contains all cart items and the total
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    @SuppressWarnings("unused")
	private Long id;
    @SuppressWarnings("unused")
	private List<CartItemResponse> items;
    @SuppressWarnings("unused")
	private BigDecimal total; // sum of all item subtotals
    @SuppressWarnings("unused")
	private Integer itemCount; // total number of items
}
