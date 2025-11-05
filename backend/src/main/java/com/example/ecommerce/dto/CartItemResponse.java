package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Response DTO for a single cart item
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    @SuppressWarnings("unused")
    private Long id;
    @SuppressWarnings("unused")
    private Long productId;
    @SuppressWarnings("unused")
    private String productName;
    @SuppressWarnings("unused")
	private BigDecimal price;
    @SuppressWarnings("unused")
	private String imageUrl;
    @SuppressWarnings("unused")
	private Integer quantity;
    @SuppressWarnings("unused")
	private BigDecimal subtotal; // price * quantity
}
