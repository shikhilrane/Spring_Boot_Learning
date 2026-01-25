package com.shikhilrane.project.collegeManagement.repositories;

import com.shikhilrane.project.collegeManagement.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}