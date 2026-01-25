package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.AdmissionResponseDto;
import com.shikhilrane.project.collegeManagement.dto.CreateAdmissionDto;
import com.shikhilrane.project.collegeManagement.services.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admissions")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    @PostMapping
    public AdmissionResponseDto create(@RequestBody CreateAdmissionDto dto) {
        return admissionService.create(dto);
    }
}
