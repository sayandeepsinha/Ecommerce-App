'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { cartAPI } from '@/lib/api';
import { isAuthenticated } from '@/lib/auth';

export default function CartPage() {
  const router = useRouter();
  const [cart, setCart] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updating, setUpdating] = useState(false);

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
      setCart(data);
    } catch (err) {
      setError('Failed to load cart');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateQuantity = async (cartItemId, newQuantity) => {
    if (newQuantity < 1) return;
    
    setUpdating(true);
    try {
      const updatedCart = await cartAPI.updateCartItem(cartItemId, newQuantity);
      setCart(updatedCart);
    } catch (err) {
      setError('Failed to update quantity');
      console.error(err);
    } finally {
      setUpdating(false);
    }
  };

  const handleRemoveItem = async (cartItemId) => {
    if (!confirm('Remove this item from cart?')) return;
    
    setUpdating(true);
    try {
      const updatedCart = await cartAPI.removeFromCart(cartItemId);
      setCart(updatedCart);
    } catch (err) {
      setError('Failed to remove item');
      console.error(err);
    } finally {
      setUpdating(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10">
        <p className="text-xl">Loading cart...</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Shopping Cart</h1>

      {error && (
        <div className="mb-4 p-3 bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 rounded">
          {error}
        </div>
      )}

      {!cart || cart.items.length === 0 ? (
        <div className="text-center py-10">
          <p className="text-xl text-gray-600 dark:text-gray-400 mb-4">Your cart is empty</p>
          <Link
            href="/"
            className="inline-block px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md"
          >
            Continue Shopping
          </Link>
        </div>
      ) : (
        <>
          {/* Cart Items */}
          <div className="space-y-4 mb-6">
            {cart.items.map((item) => (
              <div
                key={item.id}
                className="flex gap-4 p-4 border border-gray-300 dark:border-gray-600 rounded-lg"
              >
                {/* Product Image */}
                {item.imageUrl && (
                  <img
                    src={item.imageUrl}
                    alt={item.productName}
                    className="w-24 h-24 object-cover rounded"
                  />
                )}

                {/* Product Info */}
                <div className="flex-1">
                  <h3 className="font-semibold text-lg">{item.productName}</h3>
                  <p className="text-gray-600 dark:text-gray-400">${item.price}</p>

                  {/* Quantity Controls */}
                  <div className="flex items-center gap-3 mt-2">
                    <button
                      onClick={() => handleUpdateQuantity(item.id, item.quantity - 1)}
                      disabled={updating || item.quantity <= 1}
                      className="px-3 py-1 border border-gray-300 dark:border-gray-600 rounded hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50"
                    >
                      -
                    </button>
                    <span className="w-12 text-center">{item.quantity}</span>
                    <button
                      onClick={() => handleUpdateQuantity(item.id, item.quantity + 1)}
                      disabled={updating}
                      className="px-3 py-1 border border-gray-300 dark:border-gray-600 rounded hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50"
                    >
                      +
                    </button>
                  </div>
                </div>

                {/* Price & Remove */}
                <div className="text-right">
                  <p className="font-bold text-lg mb-2">${item.subtotal.toFixed(2)}</p>
                  <button
                    onClick={() => handleRemoveItem(item.id)}
                    disabled={updating}
                    className="text-red-600 hover:text-red-700 text-sm disabled:opacity-50"
                  >
                    Remove
                  </button>
                </div>
              </div>
            ))}
          </div>

          {/* Cart Summary */}
          <div className="border-t border-gray-300 dark:border-gray-600 pt-4">
            <div className="flex justify-between items-center mb-4">
              <span className="text-lg">Total Items:</span>
              <span className="text-lg font-semibold">{cart.itemCount}</span>
            </div>
            <div className="flex justify-between items-center mb-6">
              <span className="text-xl font-bold">Total:</span>
              <span className="text-2xl font-bold text-blue-600">${cart.total.toFixed(2)}</span>
            </div>

            {/* Action Buttons */}
            <div className="flex gap-4">
              <Link
                href="/"
                className="flex-1 px-6 py-3 border border-gray-300 dark:border-gray-600 rounded-md text-center hover:bg-gray-100 dark:hover:bg-gray-700"
              >
                Continue Shopping
              </Link>
              <Link
                href="/checkout"
                className="flex-1 px-6 py-3 bg-blue-600 hover:bg-blue-700 text-white rounded-md text-center font-semibold"
              >
                Proceed to Checkout
              </Link>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

