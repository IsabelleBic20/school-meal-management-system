package com.schoolmeal.mapper;

import com.schoolmeal.dto.ProductDTO;
import com.schoolmeal.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "expired", expression = "java(product.isExpired())")
    @Mapping(target = "lowStock", expression = "java(product.isLowStock())")
    ProductDTO toDTO(Product product);

    @Mapping(target = "menuProducts", ignore = true)
    @Mapping(target = "distributions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductDTO productDTO);
}
