import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Products
export const getProducts = (category, type, search) => {
  const params = {};
  if (category && category !== 'ALL') params.category = category;
  if (type && type !== 'ALL') params.type = type;
  if (search && search.trim().length > 0) params.search = search.trim();
  return api.get('/products', { params });
};

export const getProductById = (id) => api.get(`/products/${id}`);
export const createProduct = (data) => api.post('/products', data);
export const updateProduct = (id, data) => api.put(`/products/${id}`, data);
export const deleteProduct = (id) => api.delete(`/products/${id}`);

// Combos
export const getCombos = () => api.get('/combos');
export const getComboById = (id) => api.get(`/combos/${id}`);
export const createCombo = (data) => api.post('/combos', data);
export const updateCombo = (id, data) => api.put(`/combos/${id}`, data);
export const deleteCombo = (id) => api.delete(`/combos/${id}`);

// Orders
export const checkout = (data) => api.post('/orders/checkout', data);
export const getOrders = () => api.get('/orders');
export const getOrderById = (id) => api.get(`/orders/${id}`);

export default api;
