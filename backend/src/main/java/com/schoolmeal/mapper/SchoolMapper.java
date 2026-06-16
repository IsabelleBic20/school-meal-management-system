package com.schoolmeal.mapper;

import com.schoolmeal.dto.SchoolDTO;
import com.schoolmeal.entity.School;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolDTO toDTO(School school);

    School toEntity(SchoolDTO schoolDTO);
}
