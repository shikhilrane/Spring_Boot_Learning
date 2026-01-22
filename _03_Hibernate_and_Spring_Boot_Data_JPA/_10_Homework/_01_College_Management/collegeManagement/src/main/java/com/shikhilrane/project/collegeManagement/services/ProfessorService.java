package com.shikhilrane.project.collegeManagement.services;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;

import java.util.List;
import java.util.Optional;

public interface ProfessorService {
    ProfessorDto creatingProfessor(ProfessorDto professorDto);

    Optional<ProfessorDto> getProfessorById(Long id);

    List<ProfessorDto> getAllProfessor();
}
