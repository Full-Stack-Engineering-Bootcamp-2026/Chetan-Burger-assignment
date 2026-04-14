function SearchFilter({ search, setSearch, categoryFilter, setCategoryFilter, typeFilter, setTypeFilter, sortOrder, setSortOrder }) {
  const categories = [
    { label: 'All', value: '' },
    { label: 'Burger', value: 'BURGER' },
    { label: 'Fries', value: 'FRIES' },
    { label: 'Drink', value: 'DRINK' },
    { label: 'Sides', value: 'SIDES' },
    { label: 'Wrap', value: 'WRAP' },
    { label: 'Pizza', value: 'PIZZA' },
    { label: 'Meal', value: 'MEAL' },
  ];
  const types = [
    { label: 'All', value: '' },
    { label: '🥗 Veg', value: 'VEG' },
    { label: '🍗 Non-Veg', value: 'NONVEG' },
  ];

  return (
    <div className="search-filter">
      <div>
        <div>
          <input
            type="text"
            placeholder=" Search products..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>

        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1.5rem' }}>
          <div>
            <label style={{ display: 'block', marginBottom: '0.75rem', fontWeight: '600', fontSize: '0.9rem' }}>Category</label>
            <div className="filter-buttons">
              {categories.map((cat) => (
                <button
                  key={cat.value}
                  className={categoryFilter === cat.value ? 'active' : ''}
                  onClick={() => setCategoryFilter(cat.value)}
                >
                  {cat.label}
                </button>
              ))}
            </div>
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '0.75rem', fontWeight: '600', fontSize: '0.9rem' }}>Type</label>
            <div className="filter-buttons">
              {types.map((t) => (
                <button
                  key={t.value}
                  className={typeFilter === t.value ? 'active' : ''}
                  onClick={() => setTypeFilter(t.value)}
                >
                  {t.label}
                </button>
              ))}
            </div>
          </div>

          <div>
            <label style={{ display: 'block', marginBottom: '0.75rem', fontWeight: '600', fontSize: '0.9rem' }}>Sort by Price</label>
            <select value={sortOrder} onChange={(e) => setSortOrder(e.target.value)}>
              <option value="none">Default</option>
              <option value="asc">Low to High</option>
              <option value="desc">High to Low</option>
            </select>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SearchFilter;
