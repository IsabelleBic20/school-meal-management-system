package com.schoolmeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDTO {
    private Long id;

    @NotBlank(message = "Menu name cannot be blank")
    private String name;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    private List<MenuProductDTO> products;
}
