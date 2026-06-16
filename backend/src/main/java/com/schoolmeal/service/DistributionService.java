package com.schoolmeal.service;

import com.schoolmeal.dto.DistributionDTO;
import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.entity.Distribution;
import com.schoolmeal.entity.Product;
import com.schoolmeal.entity.School;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.exception.InsufficientStockException;
import com.schoolmeal.mapper.DistributionMapper;
import com.schoolmeal.repository.DistributionRepository;
import com.schoolmeal.repository.ProductRepository;
import com.schoolmeal.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final SchoolRepository schoolRepository;
    private final ProductRepository productRepository;
    private final DistributionMapper distributionMapper;
    private final ProductService productService;

    public PageDTO<DistributionDTO> getAllDistributions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Distribution> distributionPage = distributionRepository.findAll(pageable);

        return PageDTO.<DistributionDTO>builder()
                .content(distributionPage.stream()
                        .map(distributionMapper::toDTO)
                        .collect(Collectors.toList()))
                .totalPages(distributionPage.getTotalPages())
                .totalElements(distributionPage.getTotalElements())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public DistributionDTO getDistributionById(Long id) {
        Distribution distribution = distributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Distribution not found with id: " + id));
        return distributionMapper.toDTO(distribution);
    }

    public DistributionDTO createDistribution(DistributionDTO distributionDTO) {
        School school = schoolRepository.findById(distributionDTO.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + distributionDTO.getSchoolId()));

        Product product = productRepository.findById(distributionDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + distributionDTO.getProductId()));

        if (product.getStockQuantity() < distributionDTO.getQuantity()) {
            throw new InsufficientStockException(
                    "Insufficient stock for product '" + product.getName() + "'. "
                    + "Available: " + product.getStockQuantity() + ", Requested: " + distributionDTO.getQuantity());
        }

        Distribution distribution = Distribution.builder()
                .school(school)
                .product(product)
                .quantity(distributionDTO.getQuantity())
                .deliveryDate(distributionDTO.getDeliveryDate())
                .build();

        Distribution savedDistribution = distributionRepository.save(distribution);
        productService.updateStock(product.getId(), distributionDTO.getQuantity());

        return distributionMapper.toDTO(savedDistribution);
    }

    public DistributionDTO updateDistribution(Long id, DistributionDTO distributionDTO) {
        Distribution distribution = distributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Distribution not found with id: " + id));

        School school = schoolRepository.findById(distributionDTO.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + distributionDTO.getSchoolId()));

        Product product = productRepository.findById(distributionDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + distributionDTO.getProductId()));

        int quantityDifference = distributionDTO.getQuantity() - distribution.getQuantity();

        if (quantityDifference > 0 && product.getStockQuantity() < quantityDifference) {
            throw new InsufficientStockException(
                    "Insufficient stock for product '" + product.getName() + "'. "
                    + "Available: " + product.getStockQuantity() + ", Additional needed: " + quantityDifference);
        }

        if (quantityDifference != 0) {
            productService.updateStock(product.getId(), quantityDifference);
        }

        distribution.setSchool(school);
        distribution.setProduct(product);
        distribution.setQuantity(distributionDTO.getQuantity());
        distribution.setDeliveryDate(distributionDTO.getDeliveryDate());

        Distribution updatedDistribution = distributionRepository.save(distribution);
        return distributionMapper.toDTO(updatedDistribution);
    }

    public void deleteDistribution(Long id) {
        Distribution distribution = distributionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Distribution not found with id: " + id));

        productService.updateStock(distribution.getProduct().getId(), -distribution.getQuantity());
        distributionRepository.deleteById(id);
    }

    public PageDTO<DistributionDTO> getDistributionsBySchool(Long schoolId, int page, int size) {
        var distributions = distributionRepository.findBySchoolId(schoolId).stream()
                .map(distributionMapper::toDTO)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int start = pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), distributions.size());

        return PageDTO.<DistributionDTO>builder()
                .content(distributions.subList((int) start, end))
                .totalPages((distributions.size() + pageable.getPageSize() - 1) / pageable.getPageSize())
                .totalElements(distributions.size())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public PageDTO<DistributionDTO> getDistributionsByDateRange(LocalDate startDate, LocalDate endDate, int page, int size) {
        var distributions = distributionRepository.findByDeliveryDateBetween(startDate, endDate).stream()
                .map(distributionMapper::toDTO)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), distributions.size());

        return PageDTO.<DistributionDTO>builder()
                .content(distributions.subList(start, end))
                .totalPages((distributions.size() + pageable.getPageSize() - 1) / pageable.getPageSize())
                .totalElements(distributions.size())
                .currentPage(page)
                .pageSize(size)
                .build();
    }
}
