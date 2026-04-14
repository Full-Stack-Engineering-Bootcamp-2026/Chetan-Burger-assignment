import { useState, useEffect } from 'react';
import { getOrders } from '../api/api';

function OrderHistoryPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [expandedOrderId, setExpandedOrderId] = useState(null);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const response = await getOrders();
      if (response.data.success) {
        setOrders(response.data.data);
      }
    } catch (err) {
      setError('Failed to load orders');
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (isoDate) => {
    return new Date(isoDate).toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  if (loading) return <div className="loading">Loading orders...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="orders-page">
      <h1>Order History</h1>

      {orders.length === 0 ? (
        <p>No orders yet.</p>
      ) : (
        <table className="orders-table">
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Items</th>
              <th>Actual Total</th>
              <th>Optimized Total</th>
              <th>Savings</th>
              <th>Date</th>
             
            </tr>
          </thead>
          <tbody>
            {orders.map((order) => (
              <>
                <tr key={order.id}>
                  <td>#{order.id}</td>
                  <td>{order.userName}</td>
                  <td>{order.email}</td>
                  <td>{order.items?.length || 0}</td>
                  <td>₹{order.actualTotal}</td>
                  <td>₹{order.optimizedTotal}</td>
                  <td >₹{order.savings.toFixed(2)}</td>
                  <td>{formatDate(order.createdAt)}</td>
                 
                </tr>
                {expandedOrderId === order.id && (
                  <tr key={`details-${order.id}`} className="order-details">
                    <td colSpan="9">
                      <div className="details-content">
                        <div className="items-section">
                          <h4>Items Ordered:</h4>
                          <ul>
                            {order.items?.map((item, idx) => (
                              <li key={idx}>
                                {item.productName} (Qty: {item.quantity}) - ₹{item.totalPrice}
                              </li>
                            )) || <li>No items</li>}
                          </ul>
                        </div>

                        {order.appliedCombos?.length > 0 && (
                          <div className="combos-section">
                            <h4>Combos Applied:</h4>
                            <ul>
                              {order.appliedCombos.map((combo, idx) => (
                                <li key={idx}>
                                  <strong>{combo.comboName}</strong> x {combo.timesApplied} @ ₹{combo.comboPrice}
                                  <br />
                                  <small>(Saved ₹{(combo.actualPrice - combo.comboPrice) * combo.timesApplied})</small>
                                </li>
                              ))}
                            </ul>
                          </div>
                        )}
                      </div>
                    </td>
                  </tr>
                )}
              </>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default OrderHistoryPage;
