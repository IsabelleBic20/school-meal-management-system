package com.schoolmeal.service;

import com.schoolmeal.dto.ProductDTO;
import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.entity.Product;
import com.schoolmeal.exception.DuplicateEntityException;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.mapper.ProductMapper;
import com.schoolmeal.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productDTO = ProductDTO.builder()
                .id(1L)
                .name("Rice")
                .unit("kg")
                .stockQuantity(100)
                .lowStockThreshold(10)
                .expirationDate(LocalDate.now().plusMonths(1))
                .build();

        product = Product.builder()
                .id(1L)
                .name("Rice")
                .unit("kg")
                .stockQuantity(100)
                .lowStockThreshold(10)
                .expirationDate(LocalDate.now().plusMonths(1))
                .build();
    }

    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Rice", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(999L));
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateProduct_Success() {
        when(productRepository.existsByName(productDTO.getName())).thenReturn(false);
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals("Rice", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testCreateProduct_DuplicateName() {
        when(productRepository.existsByName(productDTO.getName())).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> productService.createProduct(productDTO));
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProduct_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.existsByName(productDTO.getName())).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.updateProduct(1L, productDTO);

        assertNotNull(result);
        assertEquals("Rice", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateStock_Success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.updateStock(1L, 10);

        assertEquals(90, product.getStockQuantity());
        verify(productRepository, times(1)).save(product);
    }
}
