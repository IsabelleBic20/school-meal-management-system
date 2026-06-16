import { Box, Container, Typography, CircularProgress, Alert, Pagination, Tabs, Tab } from '@mui/material';
import { useState } from 'react';
import { useProducts, useCreateProduct, useUpdateProduct, useDeleteProduct, useExpiredProducts, useLowStockProducts } from '../hooks/useProducts';
import { ProductTable } from '../components/ProductTable';
import { Product } from '../types';

export const ProductsPage = () => {
  const [page, setPage] = useState(0);
  const [tab, setTab] = useState(0);
  
  const allProducts = useProducts(page, 10);
  const expiredProducts = useExpiredProducts(page, 10);
  const lowStockProducts = useLowStockProducts(page, 10);
  
  const updateProduct = useUpdateProduct();
  const deleteProduct = useDeleteProduct();

  const currentData = tab === 0 ? allProducts.data : tab === 1 ? expiredProducts.data : lowStockProducts.data;
  const isLoading = tab === 0 ? allProducts.isLoading : tab === 1 ? expiredProducts.isLoading : lowStockProducts.isLoading;
  const error = tab === 0 ? allProducts.error : tab === 1 ? expiredProducts.error : lowStockProducts.error;

  if (isLoading) return <CircularProgress />;
  if (error) return <Alert severity="error">Error loading products</Alert>;

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Products Management
      </Typography>
      <Tabs value={tab} onChange={(_, value) => { setTab(value); setPage(0); }}>
        <Tab label="All Products" />
        <Tab label="Expired" />
        <Tab label="Low Stock" />
      </Tabs>
      {currentData && (
        <>
          <ProductTable
            products={currentData.content}
            onEdit={(product: Product) => {
              const { id, expired, lowStock, ...rest } = product;
              updateProduct.mutate({ id, product: rest });
            }}
            onDelete={(id: number) => deleteProduct.mutate(id)}
            isLoading={isLoading}
          />
          <Box sx={{ mt: 2, display: 'flex', justifyContent: 'center' }}>
            <Pagination
              count={currentData.totalPages}
              page={page + 1}
              onChange={(_, value) => setPage(value - 1)}
            />
          </Box>
        </>
      )}
    </Container>
  );
};
