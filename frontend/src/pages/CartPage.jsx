import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import CartItem from '../components/CartItem';
import BillDisplay from '../components/BillDisplay';
import { checkout } from '../api/api';

function CartPage() {
  const { cartItems, getTotalPrice, clearCart } = useCart();
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [orderResult, setOrderResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleCheckout = async (e) => {
    e.preventDefault();
    setError('');

    if (!name.trim()) {
      setError('Name is required');
      return;
    }
    if (!email || !/\S+@\S+\.\S+/.test(email)) {
      setError('Valid email is required');
      return;
    }
    if (cartItems.length === 0) {
      setError('Cart is empty');
      return;
    }

    try {
      setLoading(true);
      const requestData = {
        name: name.trim(),
        email: email.trim(),
        items: cartItems.map((item) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
      };

      const response = await checkout(requestData);
      if (response.data.success) {
        setOrderResult(response.data.data);
        clearCart();
        setName('');
        setEmail('');
      }
    } catch (err) {
      const msg = err.response?.data?.message || 'Checkout failed';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  if (orderResult) {
    return (
      <div className="cart-page mx-auto max-w-5xl px-4 py-10 sm:px-6">
        <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
          <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
            <div>
              <p className="text-sm uppercase tracking-[0.2em] text-sky-600">Order Completed</p>
              <h1 className="text-3xl font-semibold text-slate-900">Thank you for your order</h1>
            </div>
            <div className="flex gap-3">
              <button onClick={() => navigate('/orders')} className="rounded-full bg-slate-800 px-5 py-3 text-sm font-semibold text-white transition hover:bg-slate-900">
                View Orders
              </button>
              <button onClick={() => navigate('/')} className="rounded-full border border-slate-300 bg-white px-5 py-3 text-sm font-semibold text-slate-900 transition hover:bg-slate-100">
                Back to Menu
              </button>
            </div>
          </div>

          <BillDisplay orderData={orderResult} />
        </div>
      </div>
    );
  }

  return (
    <div className="cart-page max-w-6xl px-4 py-10 mx-auto sm:px-6">
      <div className="grid gap-8 lg:grid-cols-[2.2fr_1fr]">
        <div className="space-y-6">
          <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
            <div className="mb-5 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
              <div>
                <h1 className="text-3xl font-semibold text-slate-900">Your Cart</h1>
                <p className="text-sm text-slate-500">Review the items before checkout.</p>
              </div>
            </div>

            {cartItems.length === 0 ? (
              <div className="space-y-4 rounded-3xl border border-dashed border-slate-200 bg-slate-50 p-8 text-center text-slate-700">
                <p className="text-lg font-medium">Your cart is empty.</p>
                <button onClick={() => navigate('/')} className="rounded-full bg-sky-600 px-6 py-3 text-sm font-semibold text-white transition hover:bg-sky-700">
                  Browse Menu
                </button>
              </div>
            ) : (
              <div className="overflow-x-auto rounded-3xl border border-slate-200">
                <table className="w-full min-w-[700px] border-collapse text-left text-sm text-slate-700">
                  <thead className="bg-slate-50 text-slate-900">
                    <tr>
                      <th className="px-4 py-4">Product</th>
                      <th className="px-4 py-4">Price</th>
                      <th className="px-4 py-4">Qty</th>
                      <th className="px-4 py-4">Total</th>
                      <th className="px-4 py-4"></th>
                    </tr>
                  </thead>
                  <tbody>
                    {cartItems.map((item) => (
                      <CartItem key={item.productId} item={item} />
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </div>
        </div>

        <aside className="space-y-6">
          <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
            <h2 className="mb-4 text-xl font-semibold text-slate-900">Order Summary</h2>
            <div className="mb-4 rounded-3xl bg-slate-50 p-4 text-sm text-slate-700">
              <p className="font-medium">Cart total</p>
              <p className="mt-2 text-3xl font-semibold text-slate-900">₹{getTotalPrice()}</p>
            </div>
            <div className="space-y-4">
              <div className="space-y-3">
                <label className="block text-sm font-semibold text-slate-700">Name</label>
                <input
                style={{marginTop:"10px",marginLeft:"10px"}}
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  placeholder="Your full name"
                  className="w-full rounded-2xl border border-slate-300 bg-white px-4 py-3 text-sm text-slate-900 shadow-sm outline-none focus:border-sky-500 focus:ring-2 focus:ring-sky-100"
                />
              </div>
              <div className="space-y-3">
                <label className="block text-sm font-semibold text-slate-700">Email</label>
                <input
                  style={{marginTop : "10px" ,marginLeft:"10px"}}

                  type="email "
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="you@example.com"
                  className="w-full rounded-2xl border border-slate-300 bg-white px-4 py-3 text-sm text-slate-900 shadow-sm outline-none focus:border-sky-500 focus:ring-2 focus:ring-sky-100"
                />
              </div>
            </div>
            {error && <p className="mt-4 rounded-2xl bg-rose-50 px-4 py-3 text-sm text-rose-700">{error}</p>}
            <button
              type="button"
              onClick={handleCheckout}
              disabled={loading || cartItems.length === 0}
              style={{marginTop : "20px" ,borderRadius:"5px ",padding:"5px",backgroundColor:"#6cba45",fontWeight:"bold",color:"whitesmoke",}}
              className="mt-6 w-full rounded-3xl bg-sky-600 px-5 py-3 text-sm font-semibold text-white shadow-sm transition hover:bg-sky-700 disabled:cursor-not-allowed disabled:bg-slate-400"
            >
              {loading ? 'Processing order...' : 'Place Order'}
            </button>
          </div>
        </aside>
      </div>
    </div>
  );
}

export default CartPage;
