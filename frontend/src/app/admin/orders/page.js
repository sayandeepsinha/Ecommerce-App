'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { adminAPI } from '@/lib/api';
import { isAuthenticated, getUser } from '@/lib/auth';

export default function AdminOrdersPage() {
  const router = useRouter();
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updatingOrderId, setUpdatingOrderId] = useState(null);

  useEffect(() => {
    // Check if user is admin
    const user = getUser();
    if (!isAuthenticated() || user?.role !== 'ADMIN') {
      router.push('/');
      return;
    }
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await adminAPI.getOrders();
      setOrders(data);
    } catch (err) {
      setError('Failed to load orders');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleShipOrder = async (orderId) => {
    setUpdatingOrderId(orderId);
    try {
      await adminAPI.shipOrder(orderId);
      // Refresh orders list
      await fetchOrders();
    } catch (err) {
      alert('Failed to update order status');
      console.error(err);
    } finally {
      setUpdatingOrderId(null);
    }
  };

  const getStatusColor = (status) => {
    switch (status.toLowerCase()) {
      case 'pending':
        return 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900 dark:text-yellow-200';
      case 'paid':
        return 'bg-green-100 text-green-800 dark:bg-green-900 dark:text-green-200';
      case 'shipped':
        return 'bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-200';
      case 'delivered':
        return 'bg-teal-100 text-teal-800 dark:bg-teal-900 dark:text-teal-200';
      case 'cancelled':
        return 'bg-red-100 text-red-800 dark:bg-red-900 dark:text-red-200';
      default:
        return 'bg-gray-100 text-gray-800 dark:bg-gray-900 dark:text-gray-200';
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
    <div className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">All Orders</h1>
        <p className="text-gray-600 dark:text-gray-400">
          Total: {orders.length} orders
        </p>
      </div>

      {error && (
        <div className="mb-4 p-3 bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 rounded">
          {error}
        </div>
      )}

      {orders.length === 0 ? (
        <div className="text-center py-10 text-gray-600 dark:text-gray-400">
          <p className="text-xl">No orders found</p>
        </div>
      ) : (
        <div className="space-y-4">
          {orders.map((order) => (
            <div
              key={order.id}
              className="border border-gray-300 dark:border-gray-600 rounded-lg p-6 hover:shadow-lg transition-shadow"
            >
              <div className="flex justify-between items-start mb-4">
                <div>
                  <h2 className="text-xl font-semibold mb-1">
                    Order #{order.id}
                  </h2>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    User ID: {order.userId} • {new Date(order.createdAt).toLocaleString()}
                  </p>
                </div>
                <div className="flex items-center gap-3">
                  <span className={`px-3 py-1 rounded-full text-sm font-semibold ${getStatusColor(order.status)}`}>
                    {order.status.charAt(0).toUpperCase() + order.status.slice(1)}
                  </span>
                  {order.status === 'paid' && (
                    <button
                      onClick={() => handleShipOrder(order.id)}
                      disabled={updatingOrderId === order.id}
                      className="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md font-medium disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
                    >
                      {updatingOrderId === order.id ? 'Updating...' : 'Mark as Shipped'}
                    </button>
                  )}
                </div>
              </div>

              <div className="mb-4">
                <h3 className="font-semibold mb-2">Items:</h3>
                <div className="space-y-2">
                  {order.items.map((item) => (
                    <div
                      key={item.id}
                      className="flex justify-between items-center p-3 bg-gray-50 dark:bg-gray-800 rounded"
                    >
                      <div>
                        <span className="font-medium">{item.productName}</span>
                        <span className="text-gray-600 dark:text-gray-400 ml-2">
                          × {item.quantity}
                        </span>
                      </div>
                      <span className="font-semibold">${item.subtotal.toFixed(2)}</span>
                    </div>
                  ))}
                </div>
              </div>

              <div className="mb-4">
                <h3 className="font-semibold mb-1">Shipping Address:</h3>
                <p className="text-gray-700 dark:text-gray-300 whitespace-pre-line">
                  {order.shippingAddress}
                </p>
              </div>

              <div className="flex justify-between items-center pt-4 border-t border-gray-300 dark:border-gray-600">
                <span className="text-lg font-bold">Total:</span>
                <span className="text-2xl font-bold text-blue-600">
                  ${order.total.toFixed(2)}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

