import ProductCard from '../../components/productcard'

export default function ProductsPage() {
  // Later: fetch products from backend
  const sampleProducts = [
    { id: 1, name: 'Sample Product 1', price: 99 },
    { id: 2, name: 'Sample Product 2', price: 149 },
  ]

  return (
    <div>
      <h1 className="text-xl font-semibold mb-4">Products</h1>
      <div className="grid grid-cols-2 gap-4">
        {sampleProducts.map((p) => (
          <ProductCard key={p.id} product={p} />
        ))}
      </div>
    </div>
  )
}
