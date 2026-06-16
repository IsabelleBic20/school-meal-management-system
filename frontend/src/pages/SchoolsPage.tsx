import { Box, Container, Typography, CircularProgress, Alert, Pagination } from '@mui/material';
import { useState } from 'react';
import { useSchools, useCreateSchool, useUpdateSchool, useDeleteSchool } from '../hooks/useSchools';
import { SchoolTable } from '../components/SchoolTable';
import { School } from '../types';

export const SchoolsPage = () => {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useSchools(page, 10);
  const createSchool = useCreateSchool();
  const updateSchool = useUpdateSchool();
  const deleteSchool = useDeleteSchool();

  if (isLoading) return <CircularProgress />;
  if (error) return <Alert severity="error">Error loading schools</Alert>;

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Schools Management
      </Typography>
      {data && (
        <>
          <SchoolTable
            schools={data.content}
            onEdit={(school: School) => {
              const { id, ...rest } = school;
              updateSchool.mutate({ id, school: rest });
            }}
            onDelete={(id: number) => deleteSchool.mutate(id)}
            onAdd={() => {}}
          />
          <Box sx={{ mt: 2, display: 'flex', justifyContent: 'center' }}>
            <Pagination
              count={data.totalPages}
              page={page + 1}
              onChange={(_, value) => setPage(value - 1)}
            />
          </Box>
        </>
      )}
    </Container>
  );
};
