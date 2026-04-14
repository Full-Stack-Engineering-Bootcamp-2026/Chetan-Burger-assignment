import { useState } from 'react';
import { useCart } from '../context/CartContext';

function ProductCard({ product }) {
  const [quantity, setQuantity] = useState(1);
  const { addToCart } = useCart();

  const handleAddToCart = () => {
    addToCart(product, quantity);
    setQuantity(1);

    alert("added " + product.name+" :" +quantity);
  };

  const incrementQuantity = () => {
    if (quantity < 9) setQuantity(quantity + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 1) setQuantity(quantity - 1);
  };

  const vegIcon = product.type === 'VEG' ? (
    <span >
      🥗 
    </span>
  ) : (
    <span >
      🍗 
    </span>
  );

  return (
    <div className="product-card">
      <div className="product-header">
        <div>
          <h3>{product.name}</h3>
          <p className="product-category">{product.category}</p>
        </div>
        {vegIcon}
      </div>
      <div className="product-price">₹{product.price}</div>
      <div className="product-actions">
        <div className="quantity-selector">
          <button onClick={decrementQuantity}>−</button>
          <span>{quantity}</span>
          <button onClick={incrementQuantity}>+</button>
        </div>
        <button className="add-to-cart-btn" onClick={handleAddToCart} >
         Add
        </button>
      </div>
    </div>
  );
}

export default ProductCard;
