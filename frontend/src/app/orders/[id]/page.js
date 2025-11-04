'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { ordersAPI } from '@/lib/api';
import { isAuthenticated } from '@/lib/auth';
import { use } from 'react';

export default function OrderDetailPage({ params }) {
  const { id } = use(params); // Await params in Next.js 15
  const router = useRouter();
  const [order, setOrder] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!isAuthenticated()) {
      router.push('/login');
      return;
    }
    fetchOrder();
  }, []);

  const fetchOrder = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await ordersAPI.getById(id);
      setOrder(data);
    } catch (err) {
      setError('Failed to load order');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10">
        <p className="text-xl">Loading order...</p>
      </div>
    );
  }

  if (error || !order) {
    return (
      <div className="max-w-2xl mx-auto mt-10 p-6">
        <div className="bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 p-4 rounded mb-4">
          {error || 'Order not found'}
        </div>
        <Link href="/orders" className="text-blue-600 hover:underline">
          ← Back to Orders
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <Link href="/orders" className="text-blue-600 hover:underline mb-4 inline-block">
        ← Back to Orders
      </Link>

      <div className="mt-6">
        <div className="flex justify-between items-start mb-6">
          <div>
            <h1 className="text-3xl font-bold mb-2">Order #{order.id}</h1>
            <p className="text-gray-600 dark:text-gray-400">
              Placed on {new Date(order.createdAt).toLocaleString()}
            </p>
          </div>
          <span className={`px-4 py-2 rounded-full text-sm font-semibold ${
            order.status === 'pending' ? 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200' :
            order.status === 'paid' ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' :
            order.status === 'confirmed' ? 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200' :
            order.status === 'shipped' ? 'bg-purple-100 text-purple-800 dark:bg-purple-900 dark:text-purple-200' :
            order.status === 'delivered' ? 'bg-teal-100 text-teal-800 dark:bg-teal-900 dark:text-teal-200' :
            'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200'
          }`}>
            {order.status.charAt(0).toUpperCase() + order.status.slice(1)}
          </span>
        </div>

        {/* Order Items */}
        <div className="mb-6">
          <h2 className="text-2xl font-semibold mb-4">Order Items</h2>
          <div className="space-y-3">
            {order.items.map((item) => (
              <div
                key={item.id}
                className="flex justify-between items-center p-4 border border-gray-300 dark:border-gray-600 rounded"
              >
                <div>
                  <h3 className="font-semibold">{item.productName}</h3>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    ${item.price} × {item.quantity}
                  </p>
                </div>
                <p className="text-lg font-semibold">${item.subtotal.toFixed(2)}</p>
              </div>
            ))}
          </div>
        </div>

        {/* Shipping Address */}
        <div className="mb-6">
          <h2 className="text-2xl font-semibold mb-2">Shipping Address</h2>
          <div className="p-4 bg-gray-50 dark:bg-gray-800 rounded">
            <p className="whitespace-pre-line">{order.shippingAddress}</p>
          </div>
        </div>

        {/* Order Summary */}
        <div className="border-t border-gray-300 dark:border-gray-600 pt-6">
          <div className="flex justify-between items-center">
            <span className="text-2xl font-bold">Total:</span>
            <span className="text-3xl font-bold text-blue-600">${order.total.toFixed(2)}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
