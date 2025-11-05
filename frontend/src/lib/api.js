/**
 * API utility functions for backend communication
 */

import { getAuthHeader } from './auth';

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Custom API Error class with backend error details
 */
export class ApiError extends Error {
  constructor(message, status, errors = null, path = null) {
    super(message);
    this.name = 'ApiError';
    this.status = status;
    this.errors = errors; // Field validation errors
    this.path = path;
  }

  isUnauthorized() {
    return this.status === 401;
  }

  isForbidden() {
    return this.status === 403;
  }

  isConflict() {
    return this.status === 409;
  }

  isValidationError() {
    return this.status === 400 && this.errors;
  }

  getFieldError(fieldName) {
    return this.errors?.[fieldName];
  }
}

/**
 * Generic fetch wrapper with JWT authentication and enhanced error handling
 */
async function apiFetch(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  const headers = {
    'Content-Type': 'application/json',
    ...getAuthHeader(),
    ...options.headers,
  };

  const config = {
    ...options,
    headers,
  };

  try {
    const response = await fetch(url, config);
    
    if (!response.ok) {
      // Try to parse error response
      const contentType = response.headers.get('content-type');
      
      if (contentType && contentType.includes('application/json')) {
        const errorData = await response.json();
        
        // Backend ErrorResponse format: { timestamp, status, error, message, path, errors? }
        throw new ApiError(
          errorData.message || `HTTP error! status: ${response.status}`,
          response.status,
          errorData.errors, // Field validation errors map
          errorData.path
        );
      }
      
      // Fallback for non-JSON errors
      const errorText = await response.text();
      throw new ApiError(
        errorText || `HTTP error! status: ${response.status}`,
        response.status
      );
    }
    
    // Handle empty responses
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      return await response.json();
    }
    
    return await response.text();
  } catch (error) {
    // Re-throw ApiError instances
    if (error instanceof ApiError) {
      throw error;
    }
    
    // Wrap network errors
    console.error('API Error:', error);
    throw new ApiError(
      error.message || 'Network error occurred',
      0 // 0 indicates network/unknown error
    );
  }
}

/**
 * Auth API calls
 */
export const authAPI = {
  register: async (name, email, password) => {
    return apiFetch('/auth/register', {
      method: 'POST',
      body: JSON.stringify({ name, email, password }),
    });
  },

  login: async (email, password) => {
    return apiFetch('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });
  },

  registerAdmin: async (name, email, password) => {
    return apiFetch('/auth/register-admin', {
      method: 'POST',
      body: JSON.stringify({ name, email, password }),
    });
  },
};

/**
 * Products API calls
 */
export const productsAPI = {
  getAll: async (category = null, search = null) => {
    let url = '/products';
    const params = new URLSearchParams();
    
    if (category) params.append('category', category);
    if (search) params.append('search', search);
    
    const queryString = params.toString();
    if (queryString) url += `?${queryString}`;
    
    return apiFetch(url);
  },

  getById: async (id) => {
    return apiFetch(`/products/${id}`);
  },

  create: async (productData) => {
    return apiFetch('/admin/products', {
      method: 'POST',
      body: JSON.stringify(productData),
    });
  },

  update: async (id, productData) => {
    return apiFetch(`/admin/products/${id}`, {
      method: 'PUT',
      body: JSON.stringify(productData),
    });
  },

  delete: async (id) => {
    return apiFetch(`/admin/products/${id}`, {
      method: 'DELETE',
    });
  },
};

/**
 * Orders API calls (placeholder for future implementation)
 */
export const ordersAPI = {
  getAll: async () => {
    return apiFetch('/orders');
  },

  getById: async (id) => {
    return apiFetch(`/orders/${id}`);
  },

  create: async (orderData) => {
    return apiFetch('/orders', {
      method: 'POST',
      body: JSON.stringify(orderData),
    });
  },
};

/**
 * Cart API calls
 */
export const cartAPI = {
  getCart: async () => {
    return apiFetch('/cart');
  },

  addToCart: async (productId, quantity = 1) => {
    return apiFetch('/cart/add', {
      method: 'POST',
      body: JSON.stringify({ productId, quantity }),
    });
  },

  updateCartItem: async (cartItemId, quantity) => {
    return apiFetch(`/cart/update/${cartItemId}?quantity=${quantity}`, {
      method: 'PUT',
    });
  },

  removeFromCart: async (cartItemId) => {
    return apiFetch(`/cart/remove/${cartItemId}`, {
      method: 'DELETE',
    });
  },
};

/**
 * Admin API calls
 */
export const adminAPI = {
  // Get all orders (admin only)
  getOrders: async () => {
    return apiFetch('/admin/orders');
  },

  // Mark order as shipped
  shipOrder: async (orderId) => {
    return apiFetch(`/admin/orders/${orderId}/ship`, {
      method: 'PUT',
    });
  },

  // Update order status (flexible)
  updateOrderStatus: async (orderId, status) => {
    return apiFetch(`/admin/orders/${orderId}/status`, {
      method: 'PUT',
      body: JSON.stringify({ status }),
    });
  },

  // Get all products
  getProducts: async () => {
    return apiFetch('/admin/products');
  },
};

/**
 * Checkout/Payment API calls
 */
export const checkoutAPI = {
  createCheckoutSession: async (orderId) => {
    return apiFetch('/checkout/session', {
      method: 'POST',
      body: JSON.stringify({ orderId }),
    });
  },
};
