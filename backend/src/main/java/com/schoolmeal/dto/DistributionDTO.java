package com.schoolmeal.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionDTO {
    private Long id;

    @NotNull(message = "School ID cannot be null")
    private Long schoolId;

    private String schoolName;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    private String productName;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotNull(message = "Delivery date cannot be null")
    private LocalDate deliveryDate;
}
