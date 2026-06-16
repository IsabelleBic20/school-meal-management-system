import { Box, Pagination, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Chip, CircularProgress } from '@mui/material';
import { useState } from 'react';
import { Product } from '../types';
import { WarningOutlined } from '@mui/icons-material';

interface ProductTableProps {
  products: Product[];
  onEdit: (product: Product) => void;
  onDelete: (id: number) => void;
  isLoading?: boolean;
}

export const ProductTable = ({ products, onEdit, onDelete, isLoading }: ProductTableProps) => {
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
  const [formData, setFormData] = useState<Omit<Product, 'id' | 'expired' | 'lowStock'>>({
    name: '',
    unit: '',
    stockQuantity: 0,
    lowStockThreshold: 10,
  });

  const handleOpenDialog = (product?: Product) => {
    if (product) {
      setSelectedProduct(product);
      const { id, expired, lowStock, ...rest } = product;
      setFormData(rest);
    } else {
      setSelectedProduct(null);
      setFormData({ name: '', unit: '', stockQuantity: 0, lowStockThreshold: 10 });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: ['stockQuantity', 'lowStockThreshold'].includes(name) ? parseInt(value) || 0 : value,
    }));
  };

  if (isLoading) return <CircularProgress />;

  return (
    <Box>
      <Button variant="contained" sx={{ mb: 2 }} onClick={() => handleOpenDialog()}>
        Add Product
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Unit</TableCell>
              <TableCell>Stock</TableCell>
              <TableCell>Expiration</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.map(product => (
              <TableRow key={product.id}>
                <TableCell>{product.id}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.unit}</TableCell>
                <TableCell>{product.stockQuantity}</TableCell>
                <TableCell>{product.expirationDate || 'N/A'}</TableCell>
                <TableCell>
                  {product.expired && <Chip label="Expired" color="error" size="small" />}
                  {product.lowStock && <Chip label="Low Stock" color="warning" size="small" />}
                </TableCell>
                <TableCell>
                  <Button size="small" onClick={() => handleOpenDialog(product)}>
                    Edit
                  </Button>
                  <Button size="small" color="error" onClick={() => onDelete(product.id)}>
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>{selectedProduct ? 'Edit Product' : 'Add Product'}</DialogTitle>
        <DialogContent sx={{ pt: 2 }}>
          <TextField
            fullWidth
            label="Name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Unit"
            name="unit"
            value={formData.unit}
            onChange={handleInputChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Stock Quantity"
            name="stockQuantity"
            type="number"
            value={formData.stockQuantity}
            onChange={handleInputChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Low Stock Threshold"
            name="lowStockThreshold"
            type="number"
            value={formData.lowStockThreshold}
            onChange={handleInputChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Expiration Date"
            name="expirationDate"
            type="date"
            value={formData.expirationDate || ''}
            onChange={handleInputChange}
            margin="normal"
            InputLabelProps={{ shrink: true }}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button variant="contained" onClick={() => {
            if (selectedProduct) {
              onEdit({ ...selectedProduct, ...formData });
            }
            handleCloseDialog();
          }}>
            {selectedProduct ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
