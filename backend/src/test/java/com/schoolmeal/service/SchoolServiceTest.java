package com.schoolmeal.service;

import com.schoolmeal.dto.SchoolDTO;
import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.entity.School;
import com.schoolmeal.exception.DuplicateEntityException;
import com.schoolmeal.exception.EntityNotFoundException;
import com.schoolmeal.mapper.SchoolMapper;
import com.schoolmeal.repository.SchoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolMapper schoolMapper;

    @InjectMocks
    private SchoolService schoolService;

    private SchoolDTO schoolDTO;
    private School school;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        schoolDTO = SchoolDTO.builder()
                .id(1L)
                .name("Test School")
                .address("123 Main St")
                .studentCount(500)
                .build();

        school = School.builder()
                .id(1L)
                .name("Test School")
                .address("123 Main St")
                .studentCount(500)
                .build();
    }

    @Test
    void testGetAllSchools() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<School> schoolPage = new PageImpl<>(Arrays.asList(school), pageable, 1);

        when(schoolRepository.findAll(pageable)).thenReturn(schoolPage);
        when(schoolMapper.toDTO(school)).thenReturn(schoolDTO);

        PageDTO<SchoolDTO> result = schoolService.getAllSchools(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(schoolRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetSchoolById_Success() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolMapper.toDTO(school)).thenReturn(schoolDTO);

        SchoolDTO result = schoolService.getSchoolById(1L);

        assertNotNull(result);
        assertEquals("Test School", result.getName());
        verify(schoolRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSchoolById_NotFound() {
        when(schoolRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> schoolService.getSchoolById(999L));
        verify(schoolRepository, times(1)).findById(999L);
    }

    @Test
    void testCreateSchool_Success() {
        when(schoolRepository.existsByName(schoolDTO.getName())).thenReturn(false);
        when(schoolMapper.toEntity(schoolDTO)).thenReturn(school);
        when(schoolRepository.save(school)).thenReturn(school);
        when(schoolMapper.toDTO(school)).thenReturn(schoolDTO);

        SchoolDTO result = schoolService.createSchool(schoolDTO);

        assertNotNull(result);
        assertEquals("Test School", result.getName());
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    void testCreateSchool_DuplicateName() {
        when(schoolRepository.existsByName(schoolDTO.getName())).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> schoolService.createSchool(schoolDTO));
        verify(schoolRepository, never()).save(any());
    }

    @Test
    void testUpdateSchool_Success() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolRepository.existsByName(schoolDTO.getName())).thenReturn(false);
        when(schoolRepository.save(school)).thenReturn(school);
        when(schoolMapper.toDTO(school)).thenReturn(schoolDTO);

        SchoolDTO result = schoolService.updateSchool(1L, schoolDTO);

        assertNotNull(result);
        assertEquals("Test School", result.getName());
        verify(schoolRepository, times(1)).save(school);
    }

    @Test
    void testDeleteSchool_Success() {
        when(schoolRepository.existsById(1L)).thenReturn(true);

        schoolService.deleteSchool(1L);

        verify(schoolRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSchool_NotFound() {
        when(schoolRepository.existsById(999L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> schoolService.deleteSchool(999L));
        verify(schoolRepository, never()).deleteById(any());
    }
}
