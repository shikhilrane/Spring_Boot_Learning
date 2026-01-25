package com.shikhilrane.project.collegeManagement.repositories;

import com.shikhilrane.project.collegeManagement.entities.AdmissionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord, Long> {
}