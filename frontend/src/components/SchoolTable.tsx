import { Box, Pagination, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Alert } from '@mui/material';
import { useState } from 'react';
import { School } from '../types';

interface SchoolTableProps {
  schools: School[];
  onEdit: (school: School) => void;
  onDelete: (id: number) => void;
  onAdd: () => void;
}

export const SchoolTable = ({ schools, onEdit, onDelete, onAdd }: SchoolTableProps) => {
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedSchool, setSelectedSchool] = useState<School | null>(null);
  const [formData, setFormData] = useState<Omit<School, 'id'>>({ name: '', address: '', studentCount: 0 });

  const handleOpenDialog = (school?: School) => {
    if (school) {
      setSelectedSchool(school);
      setFormData({ name: school.name, address: school.address, studentCount: school.studentCount });
    } else {
      setSelectedSchool(null);
      setFormData({ name: '', address: '', studentCount: 0 });
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
      [name]: name === 'studentCount' ? parseInt(value) || 0 : value,
    }));
  };

  return (
    <Box>
      <Button variant="contained" sx={{ mb: 2 }} onClick={() => handleOpenDialog()}>
        Add School
      </Button>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Address</TableCell>
              <TableCell>Student Count</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {schools.map(school => (
              <TableRow key={school.id}>
                <TableCell>{school.id}</TableCell>
                <TableCell>{school.name}</TableCell>
                <TableCell>{school.address}</TableCell>
                <TableCell>{school.studentCount}</TableCell>
                <TableCell>
                  <Button size="small" onClick={() => handleOpenDialog(school)}>
                    Edit
                  </Button>
                  <Button size="small" color="error" onClick={() => onDelete(school.id)}>
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog} maxWidth="sm" fullWidth>
        <DialogTitle>{selectedSchool ? 'Edit School' : 'Add School'}</DialogTitle>
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
            label="Address"
            name="address"
            value={formData.address}
            onChange={handleInputChange}
            margin="normal"
          />
          <TextField
            fullWidth
            label="Student Count"
            name="studentCount"
            type="number"
            value={formData.studentCount}
            onChange={handleInputChange}
            margin="normal"
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog}>Cancel</Button>
          <Button variant="contained" onClick={() => {
            if (selectedSchool) {
              onEdit({ ...selectedSchool, ...formData });
            } else {
              onAdd();
            }
            handleCloseDialog();
          }}>
            {selectedSchool ? 'Update' : 'Create'}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
