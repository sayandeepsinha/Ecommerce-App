package com.example.ecommerce.exception;

/**
 * Exception thrown when product stock is insufficient for the requested quantity
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
