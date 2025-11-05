package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductResponse;
import com.example.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for public product endpoints.
 * Handles product browsing for all users.
 */
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products
     * GET /api/products
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search
    ) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(productService.getProductsByCategory(category));
        }
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(productService.searchProducts(search));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get product by ID
     * GET /api/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
