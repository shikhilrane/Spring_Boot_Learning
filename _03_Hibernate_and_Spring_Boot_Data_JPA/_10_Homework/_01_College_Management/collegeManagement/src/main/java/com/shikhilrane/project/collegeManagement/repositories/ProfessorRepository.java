package com.shikhilrane.project.collegeManagement.repositories;

import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
}