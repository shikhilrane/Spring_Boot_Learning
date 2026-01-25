package com.shikhilrane.project.collegeManagement.services;

import com.shikhilrane.project.collegeManagement.dto.CreateProfessorDto;
import com.shikhilrane.project.collegeManagement.dto.ProfessorResponseDto;

public interface ProfessorService {
    ProfessorResponseDto create(CreateProfessorDto dto);
}
