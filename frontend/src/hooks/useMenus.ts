import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { menuService } from '../services/menuService';
import { Menu, PageResponse } from '../types';

export const useMenus = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Menu>>({
    queryKey: ['menus', page, size],
    queryFn: () => menuService.getAll(page, size).then(res => res.data),
  });
};

export const useMenuById = (id: number) => {
  return useQuery<Menu>({
    queryKey: ['menu', id],
    queryFn: () => menuService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useCreateMenu = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (menu: Omit<Menu, 'id'>) => menuService.create(menu).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['menus'] });
    },
  });
};

export const useUpdateMenu = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, menu }: { id: number; menu: Omit<Menu, 'id'> }) =>
      menuService.update(id, menu).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['menus'] });
    },
  });
};

export const useDeleteMenu = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => menuService.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['menus'] });
    },
  });
};
