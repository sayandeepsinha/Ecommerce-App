package com.example.ecommerce.dto;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private String category;
    private String description;

    public ProductResponse() {}

    public ProductResponse(Long id, String name, BigDecimal price, Integer stock, 
                          String imageUrl, String category, String description) {
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

    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
    }

    public static class ProductResponseBuilder {
        private Long id;
        private String name;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private String category;
        private String description;

        public ProductResponseBuilder id(Long id) { this.id = id; return this; }
        public ProductResponseBuilder name(String name) { this.name = name; return this; }
        public ProductResponseBuilder price(BigDecimal price) { this.price = price; return this; }
        public ProductResponseBuilder stock(Integer stock) { this.stock = stock; return this; }
        public ProductResponseBuilder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public ProductResponseBuilder category(String category) { this.category = category; return this; }
        public ProductResponseBuilder description(String description) { this.description = description; return this; }

        public ProductResponse build() {
            return new ProductResponse(id, name, price, stock, imageUrl, category, description);
        }
    }
}
