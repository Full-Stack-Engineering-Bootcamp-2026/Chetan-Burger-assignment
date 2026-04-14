import { useState, useEffect } from 'react';
import { getProducts, getCombos } from '../api/api';
import { useCart } from '../context/CartContext';
import ProductCard from '../components/ProductCard';
import ComboCard from '../components/ComboCard';
import SearchFilter from '../components/SearchFilter';

function HomePage() {
  const [products, setProducts] = useState([]);
  const [combos, setCombos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('');
  const [typeFilter, setTypeFilter] = useState('');
  const [sortOrder, setSortOrder] = useState('none');
  const { addToCart } = useCart();

  useEffect(() => {
    fetchProducts();
  }, [categoryFilter, typeFilter, search]);

  useEffect(() => {
    fetchCombos();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const response = await getProducts(categoryFilter, typeFilter, search);
      if (response.data.success) {
        setProducts(response.data.data);
      }
    } catch (err) {
      setError('Failed to load products');
    } finally {
      setLoading(false);
    }
  };

  const fetchCombos = async () => {
    try {
      const response = await getCombos();
      if (response.data.success) {
        setCombos(response.data.data);
      }
    } catch (err) {
      console.error('Failed to load combos', err);
    }
  };

  const addComboToCart = (combo, quantity) => {
    combo.items.forEach((item) => {
      addToCart(
        {
          id: item.productId,
          name: item.productName,
          price: item.productPrice,
        },
        item.quantity * quantity
      );
    });
  };

  const sortedProducts = [...products].sort((a, b) => {
    if (sortOrder === 'asc') return a.price - b.price;
    if (sortOrder === 'desc') return b.price - a.price;
    return 0;
  });

  if (loading) return <div className="loading">Loading products...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="home-page">
    

      <SearchFilter
        search={search}
        setSearch={setSearch}
        categoryFilter={categoryFilter}
        setCategoryFilter={setCategoryFilter}
        typeFilter={typeFilter}
        setTypeFilter={setTypeFilter}
        sortOrder={sortOrder}
        setSortOrder={setSortOrder}
      />

      <section>
        <div>
             <h1> Menu</h1>
        </div>
        {sortedProducts.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '2rem', color: '#64748b' }}>
            No products match your filters.
          </div>
        ) : (
          <div className="products-grid">
            {sortedProducts.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        )}
      </section>

      <section>
        <div>
          <h2>Combo Offers</h2>
         
        </div>

        {combos.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '2rem', color: '#64748b' }}>
            No combos available.
          </div>
        ) : (
          <div className="combos-grid" style={{ gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))' }}>
            {combos.map((combo) => (
              <ComboCard key={combo.id} combo={combo} onAddCombo={addComboToCart} />
            ))}
          </div>
        )}
      </section>
    </div>
  );
}

export default HomePage;
