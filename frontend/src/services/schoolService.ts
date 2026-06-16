import apiClient from './api';
import { School, PageResponse } from '../types';

export const schoolService = {
  getAll: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<School>>('/schools', { params: { page, size } }),

  getById: (id: number) =>
    apiClient.get<School>(`/schools/${id}`),

  create: (school: Omit<School, 'id'>) =>
    apiClient.post<School>('/schools', school),

  update: (id: number, school: Omit<School, 'id'>) =>
    apiClient.put<School>(`/schools/${id}`, school),

  delete: (id: number) =>
    apiClient.delete(`/schools/${id}`),
};
