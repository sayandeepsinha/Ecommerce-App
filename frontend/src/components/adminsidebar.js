import Link from 'next/link'

export default function AdminSidebar() {
  return (
    <div className="w-48 bg-gray-100 min-h-screen p-4 border-r">
      <h2 className="font-semibold mb-4">Admin</h2>
      <ul className="flex flex-col gap-2">
        <li><Link href="/admin/products">Products</Link></li>
        <li><Link href="/admin/orders">Orders</Link></li>
      </ul>
    </div>
  )
}
