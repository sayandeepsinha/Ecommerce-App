'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import { authAPI } from '@/lib/api';
import { saveAuth } from '@/lib/auth';

export default function AdminRegisterPage() {
  const router = useRouter();
  
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    // Validation
    if (formData.password !== formData.confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    if (formData.password.length < 6) {
      setError('Password must be at least 6 characters');
      return;
    }

    setLoading(true);

    try {
      const response = await authAPI.registerAdmin(
        formData.name,
        formData.email,
        formData.password
      );
      
      // Save token and user data
      saveAuth(response.token, {
        email: response.email,
        name: response.name,
        role: response.role,
      });

      setSuccess(`Admin user "${response.name}" created successfully! Redirecting...`);
      
      // Small delay to show success message
      await new Promise(resolve => setTimeout(resolve, 1500));

      // Redirect to admin products page
      router.push('/admin/products');
      router.refresh();
    } catch (err) {
      console.error('Admin registration error:', err);
      if (err.message.includes('403')) {
        setError('Admin registration is disabled. Enable it in application.properties (app.allowAdminRegistration=true)');
      } else if (err.message.includes('400')) {
        setError('Registration failed. Email may already be in use.');
      } else {
        setError('Failed to create admin user. Check server logs.');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 p-6 bg-white dark:bg-gray-800 rounded-lg shadow-md">
      <div className="mb-6">
        <h1 className="text-3xl font-bold mb-2 text-center text-red-600">⚠️ Admin Registration</h1>
        <p className="text-sm text-gray-600 dark:text-gray-400 text-center">
          Dev/Testing Only - Creates a user with ADMIN role
        </p>
      </div>

      {error && (
        <div className="mb-4 p-3 bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 rounded">
          {error}
        </div>
      )}

      {success && (
        <div className="mb-4 p-3 bg-green-100 dark:bg-green-900 text-green-700 dark:text-green-200 rounded">
          {success}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="name" className="block mb-1 font-medium">
            Full Name
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
            placeholder="Admin User"
            className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 dark:bg-gray-700"
          />
        </div>

        <div>
          <label htmlFor="email" className="block mb-1 font-medium">
            Email
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
            placeholder="admin@example.com"
            className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 dark:bg-gray-700"
          />
        </div>

        <div>
          <label htmlFor="password" className="block mb-1 font-medium">
            Password
          </label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
            minLength={6}
            placeholder="Minimum 6 characters"
            className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 dark:bg-gray-700"
          />
        </div>

        <div>
          <label htmlFor="confirmPassword" className="block mb-1 font-medium">
            Confirm Password
          </label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
            minLength={6}
            placeholder="Re-enter password"
            className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 dark:bg-gray-700"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded-md disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {loading ? 'Creating Admin...' : 'Create Admin User'}
        </button>
      </form>

      <div className="mt-6 p-4 bg-yellow-50 dark:bg-yellow-900/20 border border-yellow-200 dark:border-yellow-800 rounded">
        <h3 className="font-semibold text-yellow-800 dark:text-yellow-200 mb-2">⚡ Important Notes:</h3>
        <ul className="text-sm text-yellow-700 dark:text-yellow-300 space-y-1">
          <li>• This page only works when <code className="bg-yellow-200 dark:bg-yellow-800 px-1 rounded">app.allowAdminRegistration=true</code></li>
          <li>• Disable this in production environments</li>
          <li>• The admin user will have full access to all admin endpoints</li>
          <li>• Password is BCrypt-hashed automatically</li>
        </ul>
      </div>

      <div className="mt-4 text-center">
        <button
          onClick={() => router.push('/')}
          className="text-blue-600 hover:underline text-sm"
        >
          ← Back to Home
        </button>
      </div>
    </div>
  );
}
