/**
 * Authentication utility functions for JWT-based auth
 */

const TOKEN_KEY = 'auth_token';
const USER_KEY = 'user_data';

// Custom event for auth state changes
export const AUTH_CHANGE_EVENT = 'authStateChanged';

/**
 * Dispatch auth change event to notify components
 */
function dispatchAuthChange() {
  if (typeof window !== 'undefined') {
    window.dispatchEvent(new Event(AUTH_CHANGE_EVENT));
  }
}

/**
 * Save authentication data to localStorage
 */
export function saveAuth(token, userData) {
  if (typeof window !== 'undefined') {
    localStorage.setItem(TOKEN_KEY, token);
    localStorage.setItem(USER_KEY, JSON.stringify(userData));
    // Notify all components that auth state changed
    dispatchAuthChange();
  }
}

/**
 * Get JWT token from localStorage
 */
export function getToken() {
  if (typeof window !== 'undefined') {
    return localStorage.getItem(TOKEN_KEY);
  }
  return null;
}

/**
 * Get user data from localStorage
 */
export function getUser() {
  if (typeof window !== 'undefined') {
    const userData = localStorage.getItem(USER_KEY);
    return userData ? JSON.parse(userData) : null;
  }
  return null;
}

/**
 * Check if user is authenticated
 */
export function isAuthenticated() {
  return !!getToken();
}

/**
 * Check if user is admin
 */
export function isAdmin() {
  const user = getUser();
  return user?.role === 'ADMIN';
}

/**
 * Logout user by clearing localStorage
 */
export function logout() {
  if (typeof window !== 'undefined') {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    // Notify components before redirect
    dispatchAuthChange();
    window.location.href = '/login';
  }
}

/**
 * Get authorization header for API requests
 */
export function getAuthHeader() {
  const token = getToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
}
