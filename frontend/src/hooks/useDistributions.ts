import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { distributionService } from '../services/distributionService';
import { Distribution, PageResponse } from '../types';

export const useDistributions = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Distribution>>({
    queryKey: ['distributions', page, size],
    queryFn: () => distributionService.getAll(page, size).then(res => res.data),
  });
};

export const useDistributionById = (id: number) => {
  return useQuery<Distribution>({
    queryKey: ['distribution', id],
    queryFn: () => distributionService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useCreateDistribution = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (distribution: Omit<Distribution, 'id' | 'schoolName' | 'productName'>) =>
      distributionService.create(distribution).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['distributions'] });
    },
  });
};

export const useUpdateDistribution = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, distribution }: { id: number; distribution: Omit<Distribution, 'id' | 'schoolName' | 'productName'> }) =>
      distributionService.update(id, distribution).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['distributions'] });
    },
  });
};

export const useDeleteDistribution = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => distributionService.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['distributions'] });
    },
  });
};
