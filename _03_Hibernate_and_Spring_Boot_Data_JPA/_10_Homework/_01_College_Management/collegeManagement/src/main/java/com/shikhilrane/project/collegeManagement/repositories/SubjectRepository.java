package com.shikhilrane.project.collegeManagement.repositories;

import com.shikhilrane.project.collegeManagement.entities.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
}