package com.schoolmeal.mapper;

import com.schoolmeal.dto.DistributionDTO;
import com.schoolmeal.entity.Distribution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DistributionMapper {

    @Mapping(target = "schoolId", source = "school.id")
    @Mapping(target = "schoolName", source = "school.name")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    DistributionDTO toDTO(Distribution distribution);

    @Mapping(target = "school", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Distribution toEntity(DistributionDTO distributionDTO);
}
