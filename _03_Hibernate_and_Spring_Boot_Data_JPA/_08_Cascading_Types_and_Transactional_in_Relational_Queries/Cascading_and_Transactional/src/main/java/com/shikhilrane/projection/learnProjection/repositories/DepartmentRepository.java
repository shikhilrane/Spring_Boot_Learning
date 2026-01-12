package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}