package com.example.ecommerce.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ProductRequest {
    
    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Product name cannot exceed 200 characters")
    private String name;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    private BigDecimal price;
    
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock cannot be negative")
    private Integer stock;
    
    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;
    
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    private String category;
    
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    public ProductRequest() {}

    public ProductRequest(String name, BigDecimal price, Integer stock, String imageUrl, 
                         String category, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.category = category;
        this.description = description;
    }

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
}
