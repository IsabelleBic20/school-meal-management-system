import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { productService } from '../services/productService';
import { Product, PageResponse } from '../types';

export const useProducts = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Product>>({
    queryKey: ['products', page, size],
    queryFn: () => productService.getAll(page, size).then(res => res.data),
  });
};

export const useProductById = (id: number) => {
  return useQuery<Product>({
    queryKey: ['product', id],
    queryFn: () => productService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useExpiredProducts = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Product>>({
    queryKey: ['products-expired', page, size],
    queryFn: () => productService.getExpired(page, size).then(res => res.data),
  });
};

export const useLowStockProducts = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Product>>({
    queryKey: ['products-low-stock', page, size],
    queryFn: () => productService.getLowStock(page, size).then(res => res.data),
  });
};

export const useCreateProduct = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (product: Omit<Product, 'id' | 'expired' | 'lowStock'>) =>
      productService.create(product).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
    },
  });
};

export const useUpdateProduct = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, product }: { id: number; product: Omit<Product, 'id' | 'expired' | 'lowStock'> }) =>
      productService.update(id, product).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
    },
  });
};

export const useDeleteProduct = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => productService.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['products'] });
    },
  });
};
