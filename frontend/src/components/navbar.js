'use client';

import Link from 'next/link';
import { useEffect, useState } from 'react';
import { isAuthenticated, isAdmin, getUser, logout, AUTH_CHANGE_EVENT } from '@/lib/auth';

export default function Navbar() {
  const [user, setUser] = useState(null);
  const [mounted, setMounted] = useState(false);

  // Function to update user state from localStorage
  const updateUserState = () => {
    if (isAuthenticated()) {
      setUser(getUser());
    } else {
      setUser(null);
    }
  };

  useEffect(() => {
    setMounted(true);
    updateUserState();

    // Listen for auth state changes
    const handleAuthChange = () => {
      updateUserState();
    };

    window.addEventListener(AUTH_CHANGE_EVENT, handleAuthChange);

    // Cleanup listener on unmount
    return () => {
      window.removeEventListener(AUTH_CHANGE_EVENT, handleAuthChange);
    };
  }, []);

  // Prevent hydration mismatch
  if (!mounted) {
    return (
      <nav className="flex justify-between items-center bg-gray-800 text-white px-6 py-3">
        <h1 className="text-lg font-semibold">MyShop</h1>
        <div className="flex gap-4">
          <Link href="/">Home</Link>
        </div>
      </nav>
    );
  }

  const handleLogout = () => {
    logout();
  };

  return (
    <nav className="flex justify-between items-center bg-gray-800 text-white px-6 py-3">
      <Link href="/">
        <h1 className="text-lg font-semibold cursor-pointer hover:text-gray-300">MyShop</h1>
      </Link>
      
      <div className="flex gap-4 items-center">
        <Link href="/" className="hover:text-gray-300">Home</Link>
        
        {user ? (
          <>
            <Link href="/cart" className="hover:text-gray-300">Cart</Link>
            <Link href="/orders" className="hover:text-gray-300">Orders</Link>
            {isAdmin() && (
              <Link href="/admin/products" className="hover:text-gray-300">Admin</Link>
            )}
            <span className="text-gray-400">|</span>
            <span className="text-sm text-gray-300">Hi, {user.name}</span>
            <button
              onClick={handleLogout}
              className="bg-red-600 hover:bg-red-700 px-3 py-1 rounded text-sm"
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link href="/login" className="hover:text-gray-300">Login</Link>
            <Link 
              href="/register"
              className="bg-blue-600 hover:bg-blue-700 px-3 py-1 rounded"
            >
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}
