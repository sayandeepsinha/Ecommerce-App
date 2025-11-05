package com.example.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    @SuppressWarnings("unused")
	private Long id;
    @SuppressWarnings("unused")
	private String name;
    @SuppressWarnings("unused")
	private BigDecimal price;
    @SuppressWarnings("unused")
	private Integer stock;
    @SuppressWarnings("unused")
	private String imageUrl;
    @SuppressWarnings("unused")
	private String category;
    @SuppressWarnings("unused")
	private String description;
}
