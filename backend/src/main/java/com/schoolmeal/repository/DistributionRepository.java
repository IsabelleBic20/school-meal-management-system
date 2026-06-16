package com.schoolmeal.repository;

import com.schoolmeal.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {
    List<Distribution> findBySchoolId(Long schoolId);

    List<Distribution> findByProductId(Long productId);

    List<Distribution> findByDeliveryDateBetween(LocalDate startDate, LocalDate endDate);
}
