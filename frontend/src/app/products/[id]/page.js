'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { productsAPI } from '@/lib/api';
import Link from 'next/link';

export default function ProductDetail({ params }) {
  const { id } = params;
  const router = useRouter();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchProduct();
  }, []);

  const fetchProduct = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await productsAPI.getById(id);
      setProduct(data);
    } catch (err) {
      setError('Failed to load product');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="text-center mt-10">
        <p className="text-xl">Loading product...</p>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="max-w-2xl mx-auto mt-10 p-6">
        <div className="bg-red-100 dark:bg-red-900 text-red-700 dark:text-red-200 p-4 rounded mb-4">
          {error || 'Product not found'}
        </div>
        <Link href="/products" className="text-blue-600 hover:underline">
          ← Back to Products
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 py-8">
      <Link href="/products" className="text-blue-600 hover:underline mb-4 inline-block">
        ← Back to Products
      </Link>

      <div className="grid md:grid-cols-2 gap-8 mt-6">
        {/* Product Image */}
        <div>
          {product.imageUrl ? (
            <img
              src={product.imageUrl}
              alt={product.name}
              className="w-full rounded-lg shadow-lg"
            />
          ) : (
            <div className="w-full h-96 bg-gray-200 dark:bg-gray-700 rounded-lg flex items-center justify-center">
              <span className="text-gray-500">No image available</span>
            </div>
          )}
        </div>

        {/* Product Info */}
        <div>
          <h1 className="text-3xl font-bold mb-4">{product.name}</h1>
          
          {product.category && (
            <span className="inline-block px-3 py-1 bg-blue-100 dark:bg-blue-900 text-blue-800 dark:text-blue-200 text-sm rounded-full mb-4">
              {product.category}
            </span>
          )}

          <p className="text-4xl font-bold text-blue-600 mb-6">${product.price}</p>

          <div className="mb-6">
            <h3 className="font-semibold mb-2">Description:</h3>
            <p className="text-gray-600 dark:text-gray-400">
              {product.description || 'No description available'}
            </p>
          </div>

          <div className="mb-6">
            <h3 className="font-semibold mb-2">Availability:</h3>
            <p className={product.stock > 0 ? 'text-green-600' : 'text-red-600'}>
              {product.stock > 0 ? `${product.stock} in stock` : 'Out of stock'}
            </p>
          </div>

          <button
            disabled={product.stock === 0}
            className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed text-white font-semibold py-3 px-6 rounded-lg transition-colors"
          >
            {product.stock > 0 ? 'Add to Cart' : 'Out of Stock'}
          </button>

          <p className="text-sm text-gray-500 dark:text-gray-400 mt-4">
            Product ID: {product.id}
          </p>
        </div>
      </div>
    </div>
  );
}
