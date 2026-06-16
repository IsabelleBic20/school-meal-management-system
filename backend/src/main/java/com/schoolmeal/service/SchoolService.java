package com.schoolmeal.service;

import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.dto.SchoolDTO;
import com.schoolmeal.entity.School;
import com.schoolmeal.exception.DuplicateEntityException;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.mapper.SchoolMapper;
import com.schoolmeal.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public PageDTO<SchoolDTO> getAllSchools(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<School> schoolPage = schoolRepository.findAll(pageable);

        return PageDTO.<SchoolDTO>builder()
                .content(schoolPage.stream()
                        .map(schoolMapper::toDTO)
                        .collect(Collectors.toList()))
                .totalPages(schoolPage.getTotalPages())
                .totalElements(schoolPage.getTotalElements())
                .currentPage(page)
                .pageSize(size)
                .build();
    }

    public SchoolDTO getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));
        return schoolMapper.toDTO(school);
    }

    public SchoolDTO createSchool(SchoolDTO schoolDTO) {
        if (schoolRepository.existsByName(schoolDTO.getName())) {
            throw new DuplicateEntityException("School with name '" + schoolDTO.getName() + "' already exists");
        }

        School school = schoolMapper.toEntity(schoolDTO);
        School savedSchool = schoolRepository.save(school);
        return schoolMapper.toDTO(savedSchool);
    }

    public SchoolDTO updateSchool(Long id, SchoolDTO schoolDTO) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id: " + id));

        if (!school.getName().equals(schoolDTO.getName()) && schoolRepository.existsByName(schoolDTO.getName())) {
            throw new DuplicateEntityException("School with name '" + schoolDTO.getName() + "' already exists");
        }

        school.setName(schoolDTO.getName());
        school.setAddress(schoolDTO.getAddress());
        school.setStudentCount(schoolDTO.getStudentCount());

        School updatedSchool = schoolRepository.save(school);
        return schoolMapper.toDTO(updatedSchool);
    }

    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new EntityNotFoundException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }
}
