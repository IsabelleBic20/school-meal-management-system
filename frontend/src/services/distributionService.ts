import apiClient from './api';
import { Distribution, PageResponse } from '../types';

export const distributionService = {
  getAll: (page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Distribution>>('/distributions', { params: { page, size } }),

  getById: (id: number) =>
    apiClient.get<Distribution>(`/distributions/${id}`),

  create: (distribution: Omit<Distribution, 'id' | 'schoolName' | 'productName'>) =>
    apiClient.post<Distribution>('/distributions', distribution),

  update: (id: number, distribution: Omit<Distribution, 'id' | 'schoolName' | 'productName'>) =>
    apiClient.put<Distribution>(`/distributions/${id}`, distribution),

  delete: (id: number) =>
    apiClient.delete(`/distributions/${id}`),

  getBySchool: (schoolId: number, page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Distribution>>(`/distributions/school/${schoolId}`, { params: { page, size } }),

  getByDateRange: (startDate: string, endDate: string, page: number = 0, size: number = 10) =>
    apiClient.get<PageResponse<Distribution>>('/distributions/date-range', {
      params: { startDate, endDate, page, size },
    }),
};
