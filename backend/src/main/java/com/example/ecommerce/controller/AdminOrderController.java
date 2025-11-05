package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Admin Order Controller
 * Handles order management for admins
 */
@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')") // All endpoints require ADMIN role
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * GET /api/admin/orders
     * Get all orders from all users
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    /**
     * PUT /api/admin/orders/{id}/ship
     * Mark an order as shipped
     */
    @PutMapping("/{id}/ship")
    public ResponseEntity<OrderResponse> shipOrder(@PathVariable Long id) {
        OrderResponse order = orderService.updateOrderStatus(id, "shipped");
        return ResponseEntity.ok(order);
    }

    /**
     * PUT /api/admin/orders/{id}/status
     * Update order status to any value (for flexibility)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String newStatus = request.get("status");
        OrderResponse order = orderService.updateOrderStatus(id, newStatus);
        return ResponseEntity.ok(order);
    }
}
