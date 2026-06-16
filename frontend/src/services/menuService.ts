import apiClient from './api';
import { Menu, PageResponse } from '../types';

export const menuService = {
  getAll: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Menu>>('/menus', { params: { page, size } }),

  getById: (id: number) =>
    apiClient.get<Menu>(`/menus/${id}`),

  create: (menu: Omit<Menu, 'id'>) =>
    apiClient.post<Menu>('/menus', menu),

  update: (id: number, menu: Omit<Menu, 'id'>) =>
    apiClient.put<Menu>(`/menus/${id}`, menu),

  delete: (id: number) =>
    apiClient.delete(`/menus/${id}`),
};
