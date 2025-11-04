'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { cartAPI, ordersAPI, checkoutAPI } from '@/lib/api';
import { isAuthenticated } from '@/lib/auth';

export default function CheckoutPage() {
  const router = useRouter();
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const [shippingAddress, setShippingAddress] = useState('');

  useEffect(() => {
    // Redirect if not logged in
    if (!isAuthenticated()) {
      router.push('/login');
      return;
    }
    fetchCart();
  }, []);

  const fetchCart = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await cartAPI.getCart();
      if (!data || data.items.length === 0) {
        // Redirect to cart if empty
        router.push('/cart');
        return;
      }
      setCart(data);
    } catch (err) {
      setError('Failed to load cart');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!shippingAddress.trim()) {
      setError('Please enter a shipping address');
      return;
    }

    setSubmitting(true);
    setError('');
    
    try {
      // Step 1: Create the order
      const order = await ordersAPI.create({ shippingAddress });
      
      // Step 2: Create Stripe checkout session
      const session = await checkoutAPI.createCheckoutSession(order.id);
      
      // Step 3: Redirect to Stripe checkout page
      window.location.href = session.checkoutUrl;
    } catch (err) {
      setError('Failed to create order. Please try again.');
      console.error(err);
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10">
        <p className="text-xl">Loading checkout...</p>
      </div>
    );
  }

  if (!cart || cart.items.length === 0) {
    return null; // Will redirect
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Checkout</h1>

      {error && (
        <div className="mb-4 p-3 bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 rounded">
          {error}
        </div>
      )}

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Order Summary */}
        <div>
          <h2 className="text-2xl font-semibold mb-4">Order Summary</h2>
          <div className="space-y-3 mb-4">
            {cart.items.map((item) => (
              <div
                key={item.id}
                className="flex justify-between items-center p-3 border border-gray-300 dark:border-gray-600 rounded"
              >
                <div className="flex gap-3">
                  {item.imageUrl && (
                    <img
                      src={item.imageUrl}
                      alt={item.productName}
                      className="w-16 h-16 object-cover rounded"
                    />
                  )}
                  <div>
                    <p className="font-semibold">{item.productName}</p>
                    <p className="text-sm text-gray-600 dark:text-gray-400">
                      Qty: {item.quantity} × ${item.price}
                    </p>
                  </div>
                </div>
                <p className="font-semibold">${item.subtotal.toFixed(2)}</p>
              </div>
            ))}
          </div>

          <div className="border-t border-gray-300 dark:border-gray-600 pt-4">
            <div className="flex justify-between items-center mb-2">
              <span>Total Items:</span>
              <span>{cart.itemCount}</span>
            </div>
            <div className="flex justify-between items-center text-xl font-bold">
              <span>Total:</span>
              <span className="text-blue-600">${cart.total.toFixed(2)}</span>
            </div>
          </div>
        </div>

        {/* Shipping Form */}
        <div>
          <h2 className="text-2xl font-semibold mb-4">Shipping Information</h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block mb-2 font-semibold">Shipping Address</label>
              <textarea
                value={shippingAddress}
                onChange={(e) => setShippingAddress(e.target.value)}
                placeholder="Enter your full shipping address"
                rows={4}
                required
                className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-md dark:bg-gray-700"
              />
            </div>

            <div className="bg-blue-50 dark:bg-blue-900 p-4 rounded-md border border-blue-200 dark:border-blue-700">
              <h3 className="font-semibold mb-2 text-blue-900 dark:text-blue-100">
                🔒 Secure Stripe Payment
              </h3>
              <p className="text-sm text-gray-700 dark:text-gray-300 mb-2">
                You'll be redirected to Stripe's secure checkout page to complete your payment.
              </p>
              <p className="text-sm text-gray-700 dark:text-gray-300">
                <strong>Test Mode:</strong> Use card{' '}
                <code className="bg-white dark:bg-gray-800 px-2 py-1 rounded font-mono">
                  4242 4242 4242 4242
                </code>
              </p>
            </div>

            <button
              type="submit"
              disabled={submitting}
              className="w-full px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-md font-semibold disabled:opacity-50 transition-colors"
            >
              {submitting ? 'Creating order...' : 'Proceed to Stripe Payment →'}
            </button>

            <button
              type="button"
              onClick={() => router.push('/cart')}
              disabled={submitting}
              className="w-full px-6 py-3 border border-gray-300 dark:border-gray-600 rounded-md hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50"
            >
              Back to Cart
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

