export default function CartItem({ item }) {
  return (
    <div className="border-b py-2 flex justify-between">
      <span>{item.name}</span>
      <span>${item.price} × {item.quantity}</span>
    </div>
  )
}
