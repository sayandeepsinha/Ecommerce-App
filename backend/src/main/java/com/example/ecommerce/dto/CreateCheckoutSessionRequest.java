package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating a Stripe checkout session
 * Contains the order ID to create payment for
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckoutSessionRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;
}
