package com.example.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Product entity represents the products table in PostgreSQL.
 * Stores product information for the e-commerce catalog.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(length = 100)
    private String category;

    @Column(length = 1000)
    private String description;
}
