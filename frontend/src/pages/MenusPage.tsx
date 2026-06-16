import { Box, Container, Typography, CircularProgress, Alert } from '@mui/material';
import { useMenus } from '../hooks/useMenus';

export const MenusPage = () => {
  const { data, isLoading, error } = useMenus(0, 10);

  if (isLoading) return <CircularProgress />;
  if (error) return <Alert severity="error">Error loading menus</Alert>;

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Menus Management
      </Typography>
      <Typography>Menus Management Page</Typography>
    </Container>
  );
};
