package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddToCartRequest;
import com.example.ecommerce.dto.CartResponse;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for cart operations
 * All endpoints require authentication
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    /**
     * Get current user's cart
     * GET /api/cart
     */
    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        CartResponse cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Add product to cart
     * POST /api/cart/add
     */
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @Valid @RequestBody AddToCartRequest request,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        CartResponse cart = cartService.addToCart(userId, request);
        return ResponseEntity.ok(cart);
    }

    /**
     * Update cart item quantity
     * PUT /api/cart/update/{cartItemId}
     */
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        CartResponse cart = cartService.updateCartItem(userId, cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }

    /**
     * Remove item from cart
     * DELETE /api/cart/remove/{cartItemId}
     */
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeFromCart(
            @PathVariable Long cartItemId,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        CartResponse cart = cartService.removeFromCart(userId, cartItemId);
        return ResponseEntity.ok(cart);
    }

    /**
     * Helper method to extract user ID from authentication
     * The authentication object contains our CustomUserDetails with the user ID
     */
    private Long getUserIdFromAuth(Authentication authentication) {
        CustomUserDetailsService.CustomUserDetails userDetails =
                (CustomUserDetailsService.CustomUserDetails) authentication.getPrincipal();
        return userDetails.getId();
    }
}
