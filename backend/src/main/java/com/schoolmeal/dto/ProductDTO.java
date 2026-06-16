package com.schoolmeal.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Unit cannot be blank")
    private String unit;

    @Positive(message = "Stock quantity must be positive")
    private Integer stockQuantity;

    private LocalDate expirationDate;

    private Integer lowStockThreshold;

    private boolean expired;

    private boolean lowStock;
}
