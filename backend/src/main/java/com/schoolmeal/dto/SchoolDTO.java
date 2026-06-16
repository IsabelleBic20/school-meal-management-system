package com.schoolmeal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolDTO {
    private Long id;

    @NotBlank(message = "School name cannot be blank")
    private String name;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @Positive(message = "Student count must be positive")
    private Integer studentCount;
}
