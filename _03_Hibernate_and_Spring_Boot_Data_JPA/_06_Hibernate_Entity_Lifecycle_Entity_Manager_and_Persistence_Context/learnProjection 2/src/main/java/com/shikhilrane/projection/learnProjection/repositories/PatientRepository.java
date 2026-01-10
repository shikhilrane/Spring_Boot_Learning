package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
