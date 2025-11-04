'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { ordersAPI } from '@/lib/api';
import { isAuthenticated } from '@/lib/auth';

export default function OrdersPage() {
  const router = useRouter();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!isAuthenticated()) {
      router.push('/login');
      return;
    }
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await ordersAPI.getAll();
      setOrders(data);
    } catch (err) {
      setError('Failed to load orders');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10">
        <p className="text-xl">Loading orders...</p>
      </div>
    );
  }

  return (
    <div className="max-w-6xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">My Orders</h1>

      {error && (
        <div className="mb-4 p-3 bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 rounded">
          {error}
        </div>
      )}

      {orders.length === 0 ? (
        <div className="text-center py-10">
          <p className="text-xl text-gray-600 dark:text-gray-400 mb-4">No orders yet</p>
          <Link
            href="/"
            className="inline-block px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md"
          >
            Start Shopping
          </Link>
        </div>
      ) : (
        <div className="space-y-6">
          {orders.map((order) => (
            <div
              key={order.id}
              className="border border-gray-300 dark:border-gray-600 rounded-lg p-6"
            >
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h2 className="text-xl font-semibold mb-1">Order #{order.id}</h2>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    Placed on {new Date(order.createdAt).toLocaleDateString()}
                  </p>
                </div>
                <span className={`px-3 py-1 rounded-full text-sm font-semibold ${
                  order.status === 'pending' ? 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200' :
                  order.status === 'paid' ? 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200' :
                  order.status === 'shipped' ? 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200' :
                  order.status === 'delivered' ? 'bg-teal-100 text-teal-800 dark:bg-teal-900 dark:text-teal-200' :
                  order.status === 'cancelled' ? 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200' :
                  'bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200'
                }`}>
                  {order.status.charAt(0).toUpperCase() + order.status.slice(1)}
                </span>
              </div>

              <div className="mb-4">
                <h3 className="font-semibold mb-2">Items:</h3>
                <div className="space-y-2">
                  {order.items.map((item) => (
                    <div
                      key={item.id}
                      className="flex justify-between items-center p-2 bg-gray-50 dark:bg-gray-800 rounded"
                    >
                      <span>
                        {item.productName} × {item.quantity}
                      </span>
                      <span className="font-semibold">${item.subtotal.toFixed(2)}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="mb-4">
                <h3 className="font-semibold mb-1">Shipping Address:</h3>
                <p className="text-gray-600 dark:text-gray-400">{order.shippingAddress}</p>
              </div>

              <div className="flex justify-between items-center pt-4 border-t border-gray-300 dark:border-gray-600">
                <span className="text-lg font-bold">Total:</span>
                <span className="text-2xl font-bold text-blue-600">${order.total.toFixed(2)}</span>
              </div>

              <Link
                href={`/orders/${order.id}`}
                className="mt-4 inline-block px-4 py-2 bg-gray-200 dark:bg-gray-700 rounded hover:bg-gray-300 dark:hover:bg-gray-600"
              >
                View Details →
              </Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

