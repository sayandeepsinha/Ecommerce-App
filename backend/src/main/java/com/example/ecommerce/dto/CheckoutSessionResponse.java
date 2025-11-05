package com.example.ecommerce.dto;

/**
 * Response DTO for Stripe checkout session
 * Contains the session ID and the checkout URL
 */
public class CheckoutSessionResponse {
    private String sessionId;
    private String checkoutUrl;

    public CheckoutSessionResponse() {}

    public CheckoutSessionResponse(String sessionId, String checkoutUrl) {
        this.sessionId = sessionId;
        this.checkoutUrl = checkoutUrl;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getCheckoutUrl() { return checkoutUrl; }
    public void setCheckoutUrl(String checkoutUrl) { this.checkoutUrl = checkoutUrl; }

    public static CheckoutSessionResponseBuilder builder() {
        return new CheckoutSessionResponseBuilder();
    }

    public static class CheckoutSessionResponseBuilder {
        private String sessionId;
        private String checkoutUrl;

        public CheckoutSessionResponseBuilder sessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public CheckoutSessionResponseBuilder checkoutUrl(String checkoutUrl) {
            this.checkoutUrl = checkoutUrl;
            return this;
        }

        public CheckoutSessionResponse build() {
            return new CheckoutSessionResponse(sessionId, checkoutUrl);
        }
    }
}
