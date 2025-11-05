package com.example.ecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Standard error response DTO
 * Provides consistent error format across all API endpoints
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @SuppressWarnings("unused")
	private LocalDateTime timestamp;
    @SuppressWarnings("unused")
	private int status;
    @SuppressWarnings("unused")
	private String error;
    @SuppressWarnings("unused")
	private String message;
    @SuppressWarnings("unused")
	private String path;
}
