package com.example.ecommerce.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Product entity represents the products table in PostgreSQL.
 * Stores product information for the e-commerce catalog.
 */
@Entity
@Table(name = "products")
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

    public Product() {}

    public Product(Long id, String name, BigDecimal price, Integer stock, String imageUrl, 
                  String category, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.category = category;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private String category;
        private String description;

        public ProductBuilder id(Long id) { this.id = id; return this; }
        public ProductBuilder name(String name) { this.name = name; return this; }
        public ProductBuilder price(BigDecimal price) { this.price = price; return this; }
        public ProductBuilder stock(Integer stock) { this.stock = stock; return this; }
        public ProductBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public ProductBuilder category(String category) { this.category = category; return this; }
        public ProductBuilder description(String description) { this.description = description; return this; }

        public Product build() {
            return new Product(id, name, price, stock, imageUrl, category, description);
        }
    }
}
