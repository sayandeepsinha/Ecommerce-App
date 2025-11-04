package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO containing Stripe checkout session URL
 * Frontend will redirect user to this URL
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutSessionResponse {
    private String sessionId;
    private String checkoutUrl;
}
