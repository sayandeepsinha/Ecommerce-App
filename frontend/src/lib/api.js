/**
 * API utility functions for backend communication
 */

import { getAuthHeader } from './auth';

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * Generic fetch wrapper with JWT authentication
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
      const error = await response.text();
      throw new Error(error || `HTTP error! status: ${response.status}`);
    }
    
    // Handle empty responses
    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      return await response.json();
    }
    
    return await response.text();
  } catch (error) {
    console.error('API Error:', error);
    throw error;
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
 * Admin API calls (placeholder for future implementation)
 */
export const adminAPI = {
  getOrders: async () => {
    return apiFetch('/admin/orders');
  },

  getProducts: async () => {
    return apiFetch('/admin/products');
  },
};
