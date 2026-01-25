package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.CreateProfessorDto;
import com.shikhilrane.project.collegeManagement.dto.ProfessorResponseDto;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

    @PostMapping
    public ProfessorResponseDto create(@RequestBody CreateProfessorDto dto) {
        return professorService.create(dto);
    }
}
