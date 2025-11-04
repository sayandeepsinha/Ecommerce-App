package com.example.ecommerce.service;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderItemResponse;
import com.example.ecommerce.dto.OrderResponse;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for order operations
 * Handles creating orders from cart and retrieving order history
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;

    /**
     * Create an order from the user's cart
     * Converts cart items to order items and clears the cart
     */
    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {
        // Get user's cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        // Check if cart is empty
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create order from empty cart");
        }
        
        // Create new order
        Order order = Order.builder()
                .userId(userId)
                .status("pending")
                .shippingAddress(request.getShippingAddress())
                .build();
        
        // Convert cart items to order items
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(cartItem.getProduct().getId())
                    .productName(cartItem.getProduct().getName())
                    .price(cartItem.getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();
            
            order.addItem(orderItem);
            total = total.add(orderItem.getSubtotal());
        }
        
        order.setTotal(total);
        
        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Clear the cart
        cartService.clearCart(userId);
        
        return convertToResponse(savedOrder);
    }

    /**
     * Get all orders for a user
     */
    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get a specific order by ID
     */
    public OrderResponse getOrderById(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Verify the order belongs to this user
        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Order does not belong to this user");
        }
        
        return convertToResponse(order);
    }

    // ============ ADMIN METHODS ============

    /**
     * Get all orders (admin only)
     * Returns all orders from all users
     */
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Update order status (admin only)
     * Common statuses: "pending", "paid", "shipped", "delivered", "cancelled"
     */
    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(newStatus);
        orderRepository.save(order);
        
        return convertToResponse(order);
    }

    // ============ HELPER METHODS ============

    /**
     * Helper method: Convert Order entity to OrderResponse DTO
     */
    private OrderResponse convertToResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());
        
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .items(itemResponses)
                .total(order.getTotal())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .shippingAddress(order.getShippingAddress())
                .build();
    }

    /**
     * Helper method: Convert OrderItem entity to OrderItemResponse DTO
     */
    private OrderItemResponse convertItemToResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .build();
    }
}
