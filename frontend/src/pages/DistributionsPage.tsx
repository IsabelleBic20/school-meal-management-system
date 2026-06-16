import { Box, Container, Typography, CircularProgress, Alert } from '@mui/material';
import { useDistributions } from '../hooks/useDistributions';

export const DistributionsPage = () => {
  const { data, isLoading, error } = useDistributions(0, 10);

  if (isLoading) return <CircularProgress />;
  if (error) return <Alert severity="error">Error loading distributions</Alert>;

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Typography variant="h4" gutterBottom>
        Distributions Management
      </Typography>
      <Typography>Distributions Management Page</Typography>
    </Container>
  );
};
