import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { schoolService } from '../services/schoolService';
import { School, PageResponse } from '../types';

export const useSchools = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<School>>({
    queryKey: ['schools', page, size],
    queryFn: () => schoolService.getAll(page, size).then(res => res.data),
  });
};

export const useSchoolById = (id: number) => {
  return useQuery<School>({
    queryKey: ['school', id],
    queryFn: () => schoolService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useCreateSchool = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (school: Omit<School, 'id'>) => schoolService.create(school).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['schools'] });
    },
  });
};

export const useUpdateSchool = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ id, school }: { id: number; school: Omit<School, 'id'> }) =>
      schoolService.update(id, school).then(res => res.data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['schools'] });
    },
  });
};

export const useDeleteSchool = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (id: number) => schoolService.delete(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['schools'] });
    },
  });
};
