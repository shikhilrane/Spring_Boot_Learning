package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.entities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}