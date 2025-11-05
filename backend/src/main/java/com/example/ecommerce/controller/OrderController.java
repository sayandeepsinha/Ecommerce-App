package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.service.CustomUserDetailsService;
import com.example.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for order operations
 * All endpoints require authentication
 */
@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Create an order from cart
     * POST /api/orders
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        OrderResponse order = orderService.createOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    /**
     * Get all orders for current user
     * GET /api/orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getUserOrders(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<OrderResponse> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get a specific order by ID
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        OrderResponse order = orderService.getOrderById(userId, id);
        return ResponseEntity.ok(order);
    }

    /**
     * Helper method to extract user ID from authentication
     */
    private Long getUserIdFromAuth(Authentication authentication) {
        CustomUserDetailsService.CustomUserDetails userDetails =
                (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
