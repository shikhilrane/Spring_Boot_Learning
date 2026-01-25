package com.shikhilrane.project.collegeManagement.services;

import com.shikhilrane.project.collegeManagement.dto.CreateStudentDto;
import com.shikhilrane.project.collegeManagement.dto.StudentResponseDto;

public interface StudentService {
    StudentResponseDto create(CreateStudentDto dto);
    void assignProfessor(Long studentId, Long professorId);
}
