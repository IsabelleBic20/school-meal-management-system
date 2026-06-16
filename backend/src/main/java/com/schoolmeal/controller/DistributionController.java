package com.schoolmeal.controller;

import com.schoolmeal.dto.DistributionDTO;
import com.schoolmeal.dto.PageDTO;
import com.schoolmeal.service.DistributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/distributions")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class DistributionController {

    private final DistributionService distributionService;

    @GetMapping
    public ResponseEntity<PageDTO<DistributionDTO>> getAllDistributions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(distributionService.getAllDistributions(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DistributionDTO> getDistributionById(@PathVariable Long id) {
        return ResponseEntity.ok(distributionService.getDistributionById(id));
    }

    @PostMapping
    public ResponseEntity<DistributionDTO> createDistribution(@Valid @RequestBody DistributionDTO distributionDTO) {
        DistributionDTO created = distributionService.createDistribution(distributionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DistributionDTO> updateDistribution(
            @PathVariable Long id,
            @Valid @RequestBody DistributionDTO distributionDTO) {
        return ResponseEntity.ok(distributionService.updateDistribution(id, distributionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistribution(@PathVariable Long id) {
        distributionService.deleteDistribution(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<PageDTO<DistributionDTO>> getDistributionsBySchool(
            @PathVariable Long schoolId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(distributionService.getDistributionsBySchool(schoolId, page, size));
    }

    @GetMapping("/date-range")
    public ResponseEntity<PageDTO<DistributionDTO>> getDistributionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(distributionService.getDistributionsByDateRange(startDate, endDate, page, size));
    }
}
