import apiClient from './api';
import { Product, PageResponse } from '../types';

export const productService = {
  getAll: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Product>>('/products', { params: { page, size } }),

  getById: (id: number) =>
    apiClient.get<Product>(`/products/${id}`),

  create: (product: Omit<Product, 'id' | 'expired' | 'lowStock'>) =>
    apiClient.post<Product>('/products', product),

  update: (id: number, product: Omit<Product, 'id' | 'expired' | 'lowStock'>) =>
    apiClient.put<Product>(`/products/${id}`, product),

  delete: (id: number) =>
    apiClient.delete(`/products/${id}`),

  getExpired: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Product>>('/products/expired', { params: { page, size } }),

  getLowStock: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Product>>('/products/low-stock', { params: { page, size } }),
};
