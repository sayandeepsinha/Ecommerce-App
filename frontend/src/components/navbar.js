import Link from 'next/link'

export default function Navbar() {
  return (
    <nav className="flex justify-between items-center bg-gray-800 text-white px-6 py-3">
      <h1 className="text-lg font-semibold">MyShop</h1>
      <div className="flex gap-4">
        <Link href="/">Home</Link>
        <Link href="/products">Products</Link>
        <Link href="/cart">Cart</Link>
        <Link href="/orders">Orders</Link>
        <Link href="/admin/products">Admin</Link>
      </div>
    </nav>
  )
}
