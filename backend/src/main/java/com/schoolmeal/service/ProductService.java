package com.schoolmeal.service;

import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.dto.ProductDTO;
import com.schoolmeal.entity.Product;
import com.schoolmeal.exception.DuplicateEntityException;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.mapper.ProductMapper;
import com.schoolmeal.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public PageDTO<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        return PageDTO.<ProductDTO>builder()
                .content(productPage.stream()
                        .map(productMapper::toDTO)
                        .collect(Collectors.toList()))
                .totalPages(productPage.getTotalPages())
                .totalElements(productPage.getTotalElements())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productRepository.existsByName(productDTO.getName())) {
            throw new DuplicateEntityException("Product with name '" + productDTO.getName() + "' already exists");
        }

        Product product = productMapper.toEntity(productDTO);
        if (product.getLowStockThreshold() == null) {
            product.setLowStockThreshold(10);
        }

        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        if (!product.getName().equals(productDTO.getName()) && productRepository.existsByName(productDTO.getName())) {
            throw new DuplicateEntityException("Product with name '" + productDTO.getName() + "' already exists");
        }

        product.setName(productDTO.getName());
        product.setUnit(productDTO.getUnit());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setExpirationDate(productDTO.getExpirationDate());
        if (productDTO.getLowStockThreshold() != null) {
            product.setLowStockThreshold(productDTO.getLowStockThreshold());
        }

        Product updatedProduct = productRepository.save(product);
        return productMapper.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public PageDTO<ProductDTO> getExpiredProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var expiredProducts = productRepository.findExpiredProducts().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        int start = Math.min(page * size, expiredProducts.size());
        int end = Math.min(start + size, expiredProducts.size());
        var paginatedProducts = expiredProducts.subList(start, end);

        return PageDTO.<ProductDTO>builder()
                .content(paginatedProducts)
                .totalPages((expiredProducts.size() + size - 1) / size)
                .totalElements(expiredProducts.size())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public PageDTO<ProductDTO> getLowStockProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var lowStockProducts = productRepository.findLowStockProducts().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());

        int start = Math.min(page * size, lowStockProducts.size());
        int end = Math.min(start + size, lowStockProducts.size());
        var paginatedProducts = lowStockProducts.subList(start, end);

        return PageDTO.<ProductDTO>builder()
                .content(paginatedProducts)
                .totalPages((lowStockProducts.size() + size - 1) / size)
                .totalElements(lowStockProducts.size())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public void updateStock(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }
}
