package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CheckoutSessionResponse;
import com.example.ecommerce.dto.CreateCheckoutSessionRequest;
import com.example.ecommerce.service.CustomUserDetailsService;
import com.example.ecommerce.service.StripeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for Stripe checkout operations
 * Handles creating payment sessions and processing webhooks
 */
@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CheckoutController {

    private final StripeService stripeService;

    /**
     * Create a Stripe checkout session for an order
     * POST /api/checkout/session
     * 
     * @param request Contains the order ID to create payment for
     * @param authentication Current authenticated user
     * @return Stripe checkout URL to redirect user to
     */
    @PostMapping("/session")
    public ResponseEntity<CheckoutSessionResponse> createCheckoutSession(
            @Valid @RequestBody CreateCheckoutSessionRequest request,
            Authentication authentication
    ) {
        Long userId = getUserIdFromAuth(authentication);
        CheckoutSessionResponse response = stripeService.createCheckoutSession(
                request.getOrderId(), 
                userId
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Stripe webhook endpoint
     * POST /api/checkout/webhook
     * 
     * This endpoint is called by Stripe when payment events occur
     * Must be publicly accessible (no authentication required)
     * 
     * @param payload Raw webhook payload from Stripe
     * @param sigHeader Stripe signature header for verification
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        try {
            stripeService.handleWebhook(payload, sigHeader);
            return ResponseEntity.ok("Webhook processed");
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook processing failed");
        }
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
