package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}