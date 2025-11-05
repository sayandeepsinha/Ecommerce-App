package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request DTO for creating an order
 */
public class CreateOrderRequest {
    
    @NotBlank(message = "Shipping address is required")
    @Size(min = 10, max = 500, message = "Shipping address must be between 10 and 500 characters")
    private String shippingAddress;

    public CreateOrderRequest() {}

    public CreateOrderRequest(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public static CreateOrderRequestBuilder builder() {
        return new CreateOrderRequestBuilder();
    }

    public static class CreateOrderRequestBuilder {
        private String shippingAddress;

        public CreateOrderRequestBuilder shippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public CreateOrderRequest build() {
            return new CreateOrderRequest(shippingAddress);
        }
    }
}
