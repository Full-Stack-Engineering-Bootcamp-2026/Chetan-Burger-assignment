import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';

function Navbar() {
  const { getTotalItems } = useCart();

  return (
    <nav className="navbar">
      <div className="navbar-brand">
        <Link to="/">🍔 Burgerly</Link>
      </div>
      <div className="navbar-links">
        <Link to="/"> Home</Link>
        <Link to="/cart" style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
          🛒 Cart [{getTotalItems()}]
        </Link>
        <Link to="/orders"> Orders</Link>
      </div>
    </nav>
  );
}

export default Navbar;
