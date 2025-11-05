/**
 * Frontend error handling utilities
 */

import { ApiError } from './api';

/**
 * Get user-friendly error message from ApiError
 */
export function getErrorMessage(error) {
  if (!(error instanceof ApiError)) {
    return error?.message || 'An unexpected error occurred';
  }

  // Handle specific status codes
  switch (error.status) {
    case 401:
      return 'You need to log in to access this resource';
    case 403:
      return 'You do not have permission to perform this action';
    case 404:
      return 'The requested resource was not found';
    case 409:
      return error.message || 'Conflict occurred (e.g., insufficient stock)';
    case 400:
      if (error.isValidationError()) {
        // Return first validation error or generic message
        const firstError = Object.values(error.errors || {})[0];
        return firstError || 'Invalid input provided';
      }
      return error.message || 'Bad request';
    case 500:
      return 'Server error occurred. Please try again later';
    case 0:
      return 'Network error. Please check your connection';
    default:
      return error.message || 'An error occurred';
  }
}

/**
 * Get all validation errors as an object
 */
export function getValidationErrors(error) {
  if (error instanceof ApiError && error.isValidationError()) {
    return error.errors || {};
  }
  return {};
}

/**
 * Check if error requires redirect to login
 */
export function shouldRedirectToLogin(error) {
  return error instanceof ApiError && error.isUnauthorized();
}

/**
 * Check if error requires redirect to unauthorized page
 */
export function shouldShowUnauthorized(error) {
  return error instanceof ApiError && error.isForbidden();
}

/**
 * Format stock error message
 */
export function getStockErrorMessage(error) {
  if (error instanceof ApiError && error.isConflict()) {
    return error.message || 'Insufficient stock available';
  }
  return null;
}

/**
 * Display error to user (can be extended with toast library)
 */
export function displayError(error, fallbackMessage = 'An error occurred') {
  const message = getErrorMessage(error) || fallbackMessage;
  
  // For now, use alert (can be replaced with toast library)
  // TODO: Replace with react-hot-toast or similar
  if (typeof window !== 'undefined') {
    console.error('Error:', error);
    // Uncomment when toast library is added:
    // toast.error(message);
  }
  
  return message;
}

/**
 * Handle API errors with automatic redirect logic
 */
export function handleApiError(error, router = null) {
  // Log error for debugging
  console.error('API Error:', error);

  // Redirect to login on 401
  if (shouldRedirectToLogin(error) && router) {
    router.push('/login?error=unauthorized');
    return;
  }

  // Redirect to unauthorized page on 403
  if (shouldShowUnauthorized(error) && router) {
    router.push('/unauthorized');
    return;
  }

  // Display error message
  return displayError(error);
}
