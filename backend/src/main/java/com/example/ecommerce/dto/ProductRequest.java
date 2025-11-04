package com.example.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private String category;
    private String description;
}
