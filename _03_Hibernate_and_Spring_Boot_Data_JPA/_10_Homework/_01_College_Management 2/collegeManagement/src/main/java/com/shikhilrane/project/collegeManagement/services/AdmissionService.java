package com.shikhilrane.project.collegeManagement.services;

import com.shikhilrane.project.collegeManagement.dto.AdmissionResponseDto;
import com.shikhilrane.project.collegeManagement.dto.CreateAdmissionDto;

public interface AdmissionService {
    AdmissionResponseDto create(CreateAdmissionDto dto);
}
