import { useState } from 'react';

function ComboCard({ combo, onAddCombo }) {
  const [quantity, setQuantity] = useState(1);
  const totalItems = combo.items?.reduce((sum, item) => sum + item.quantity, 0) || 0;

  const handleAdd = () => {
    onAddCombo(combo, quantity);
    setQuantity(1);
  };

  const incrementQuantity = () => {
    if (quantity < 5) setQuantity(quantity + 1);
  };

  const decrementQuantity = () => {
    if (quantity > 1) setQuantity(quantity - 1);
  };

  return (
    <div className="combo-card">
      <h3>{combo.name}</h3>
      <div className="product-price">₹{combo.comboPrice}</div>
      <div >
        <ul>
          {combo.items.map((item) => (
            <li key={item.productId}>
              {item.productName} 
            </li>
          ))}
        </ul>
      </div>
      <div className="combo-actions">
        <div className="quantity-selector">
          <button onClick={decrementQuantity}>−</button>
          <span>{quantity}</span>
          <button onClick={incrementQuantity}>+</button>
        </div>
        <button className="add-combo-btn" onClick={handleAdd}>
          Add
        </button>
      </div>
    </div>
  );
}

export default ComboCard;
