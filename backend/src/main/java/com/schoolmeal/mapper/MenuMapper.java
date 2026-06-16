package com.schoolmeal.mapper;

import com.schoolmeal.dto.MenuDTO;
import com.schoolmeal.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MenuProductMapper.class)
public interface MenuMapper {

    @Mapping(target = "products", source = "menuProducts")
    MenuDTO toDTO(Menu menu);

    @Mapping(target = "menuProducts", source = "products")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Menu toEntity(MenuDTO menuDTO);
}
