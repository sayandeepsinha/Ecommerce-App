package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Request DTO for creating a Stripe checkout session
 * Contains the order ID to create payment for
 */
public class CreateCheckoutSessionRequest {
    
    @NotNull(message = "Order ID is required")
    private Long orderId;

    public CreateCheckoutSessionRequest() {}

    public CreateCheckoutSessionRequest(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public static CreateCheckoutSessionRequestBuilder builder() {
        return new CreateCheckoutSessionRequestBuilder();
    }

    public static class CreateCheckoutSessionRequestBuilder {
        private Long orderId;

        public CreateCheckoutSessionRequestBuilder orderId(Long orderId) {
            this.orderId = orderId;
            return this;
        }

        public CreateCheckoutSessionRequest build() {
            return new CreateCheckoutSessionRequest(orderId);
        }
    }
}
