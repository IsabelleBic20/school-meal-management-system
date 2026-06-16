package com.schoolmeal.service;

import com.schoolmeal.dto.DistributionDTO;
import com.schoolmeal.entity.Distribution;
import com.schoolmeal.entity.Product;
import com.schoolmeal.entity.School;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.exception.InsufficientStockException;
import com.schoolmeal.mapper.DistributionMapper;
import com.schoolmeal.repository.DistributionRepository;
import com.schoolmeal.repository.ProductRepository;
import com.schoolmeal.repository.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DistributionServiceTest {

    @Mock
    private DistributionRepository distributionRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DistributionMapper distributionMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private DistributionService distributionService;

    private DistributionDTO distributionDTO;
    private Distribution distribution;
    private School school;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        school = School.builder()
                .id(1L)
                .name("Test School")
                .address("123 Main St")
                .studentCount(500)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Rice")
                .unit("kg")
                .stockQuantity(100)
                .lowStockThreshold(10)
                .build();

        distributionDTO = DistributionDTO.builder()
                .schoolId(1L)
                .productId(1L)
                .quantity(10)
                .deliveryDate(LocalDate.now())
                .build();

        distribution = Distribution.builder()
                .id(1L)
                .school(school)
                .product(product)
                .quantity(10)
                .deliveryDate(LocalDate.now())
                .build();
    }

    @Test
    void testCreateDistribution_Success() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(distributionRepository.save(any())).thenReturn(distribution);
        when(distributionMapper.toDTO(distribution)).thenReturn(distributionDTO);

        DistributionDTO result = distributionService.createDistribution(distributionDTO);

        assertNotNull(result);
        verify(productService, times(1)).updateStock(1L, 10);
        verify(distributionRepository, times(1)).save(any());
    }

    @Test
    void testCreateDistribution_InsufficientStock() {
        product.setStockQuantity(5);
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> distributionService.createDistribution(distributionDTO));
        verify(distributionRepository, never()).save(any());
    }

    @Test
    void testCreateDistribution_SchoolNotFound() {
        when(schoolRepository.findById(999L)).thenReturn(Optional.empty());
        distributionDTO.setSchoolId(999L);

        assertThrows(EntityNotFoundException.class, () -> distributionService.createDistribution(distributionDTO));
        verify(distributionRepository, never()).save(any());
    }

    @Test
    void testCreateDistribution_ProductNotFound() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        distributionDTO.setProductId(999L);

        assertThrows(EntityNotFoundException.class, () -> distributionService.createDistribution(distributionDTO));
        verify(distributionRepository, never()).save(any());
    }

    @Test
    void testDeleteDistribution_Success() {
        when(distributionRepository.findById(1L)).thenReturn(Optional.of(distribution));

        distributionService.deleteDistribution(1L);

        verify(productService, times(1)).updateStock(1L, -10);
        verify(distributionRepository, times(1)).deleteById(1L);
    }
}
