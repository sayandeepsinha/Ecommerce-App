package com.example.ecommerce.service;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartItemResponse;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for cart operations
 * Handles all business logic for shopping cart
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Get user's cart
     * If cart doesn't exist, create a new one
     */
    @Transactional
    public CartResponse getCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return convertToResponse(cart);
    }

    /**
     * Add a product to the cart
     * If product already exists, increase quantity
     */
    @Transactional
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        // Get or create cart
        Cart cart = getOrCreateCart(userId);
        
        // Get the product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        // Check if product is already in cart
        CartItem existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .orElse(null);
        
        if (existingItem != null) {
            // Product already in cart - increase quantity
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem);
        } else {
            // New product - create new cart item
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
        }
        
        return convertToResponse(cart);
    }

    /**
     * Update cart item quantity
     */
    @Transactional
    public CartResponse updateCartItem(Long userId, Long cartItemId, Integer newQuantity) {
        Cart cart = getOrCreateCart(userId);
        
        // Find the cart item
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        // Verify the item belongs to this user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }
        
        if (newQuantity <= 0) {
            // If quantity is 0 or negative, remove the item
            cart.removeItem(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            // Update quantity
            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
        }
        
        return convertToResponse(cart);
    }

    /**
     * Remove an item from cart
     */
    @Transactional
    public CartResponse removeFromCart(Long userId, Long cartItemId) {
        Cart cart = getOrCreateCart(userId);
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        // Verify the item belongs to this user's cart
        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }
        
        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        
        return convertToResponse(cart);
    }

    /**
     * Clear all items from cart
     */
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    /**
     * Helper method: Get cart or create if it doesn't exist
     */
    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });
    }

    /**
     * Helper method: Convert Cart entity to CartResponse DTO
     */
    private CartResponse convertToResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());
        
        // Calculate total
        BigDecimal total = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        // Calculate item count
        Integer itemCount = cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        
        return CartResponse.builder()
                .id(cart.getId())
                .items(itemResponses)
                .total(total)
                .itemCount(itemCount)
                .build();
    }

    /**
     * Helper method: Convert CartItem entity to CartItemResponse DTO
     */
    private CartItemResponse convertItemToResponse(CartItem item) {
        return CartItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .imageUrl(item.getProduct().getImageUrl())
                .quantity(item.getQuantity())
                .subtotal(item.getSubtotal())
                .build();
    }
}
