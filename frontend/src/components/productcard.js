import Link from 'next/link'

export default function ProductCard({ product }) {
  return (
    <div className="border rounded p-4 text-center">
      <h2 className="font-medium">{product.name}</h2>
      <p className="text-gray-500 mt-1">${product.price}</p>
      <Link
        href={`/products/${product.id}`}
        className="text-blue-600 underline text-sm mt-2 inline-block"
      >
        View Details
      </Link>
    </div>
  )
}
