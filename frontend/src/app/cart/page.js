import CartItem from '../../components/cartitem'

export default function CartPage() {
  const cartItems = [{ id: 1, name: 'Sample Product', price: 99, quantity: 1 }]

  const total = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0)

  return (
    <div>
      <h1 className="text-xl font-semibold mb-4">Your Cart</h1>
      {cartItems.map((item) => (
        <CartItem key={item.id} item={item} />
      ))}
      <div className="text-right mt-4 font-semibold">Total: ${total}</div>
    </div>
  )
}
