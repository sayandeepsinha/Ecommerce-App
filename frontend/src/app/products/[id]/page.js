export default function ProductDetail({ params }) {
  const { id } = params
  return (
    <div>
      <h1 className="text-xl font-semibold">Product Details</h1>
      <p className="mt-2 text-gray-700">Showing details for product ID: {id}</p>
    </div>
  )
}
