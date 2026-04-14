import { useCart } from '../context/CartContext';

function CartItem({ item }) {
  const { updateQuantity, removeFromCart } = useCart();

  const incrementQuantity = () => {
    if (item.quantity < 9) updateQuantity(item.productId, item.quantity + 1);
  };

  const decrementQuantity = () => {
    if (item.quantity > 1) updateQuantity(item.productId, item.quantity - 1);
  };

  return (
    <tr>
      <td>{item.productName}</td>
      <td>₹{item.price}</td>
      <td>
        <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          <button
            onClick={decrementQuantity}
            style={{
              background: 'none',
              border: '1px solid #e2e8f0',
              padding: '0.25rem 0.4rem',
              borderRadius: '0.35rem',
            }}
          >
            −
          </button>
          <span style={{ minWidth: '1.5rem', textAlign: 'center' }}>{item.quantity}</span>
          <button
            onClick={incrementQuantity}
            style={{
              background: 'none',
              border: '1px solid #e2e8f0',
              padding: '0.25rem 0.4rem',
              borderRadius: '0.35rem',
            }}
          >
            +
          </button>
        </div>
      </td>
      <td>₹{(item.price * item.quantity).toFixed(2)}</td>
      <td>
        <button className="btn-remove " onClick={() => removeFromCart(item.productId)}>
           Remove
        </button>
      </td>
    </tr>
  );
}

export default CartItem;
