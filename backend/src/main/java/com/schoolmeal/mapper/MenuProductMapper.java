package com.schoolmeal.mapper;

import com.schoolmeal.dto.MenuProductDTO;
import com.schoolmeal.entity.MenuProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuProductMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    MenuProductDTO toDTO(MenuProduct menuProduct);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "menu", ignore = true)
    MenuProduct toEntity(MenuProductDTO menuProductDTO);
}
