export interface School {
  id: number;
  name: string;
  address: string;
  studentCount: number;
}

export interface Product {
  id: number;
  name: string;
  unit: string;
  stockQuantity: number;
  expirationDate?: string;
  lowStockThreshold: number;
  expired: boolean;
  lowStock: boolean;
}

export interface MenuProduct {
  productId: number;
  productName: string;
  quantity: number;
}

export interface Menu {
  id: number;
  name: string;
  date: string;
  products: MenuProduct[];
}

export interface Distribution {
  id: number;
  schoolId: number;
  schoolName: string;
  productId: number;
  productName: string;
  quantity: number;
  deliveryDate: string;
}

export interface PageResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  currentPage: number;
  pageSize: number;
}

export interface AuthResponse {
  token: string;
  message: string;
}
