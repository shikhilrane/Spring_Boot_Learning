package com.shikhilrane.project.collegeManagement.services;

import com.shikhilrane.project.collegeManagement.dto.CreateSubjectDto;

public interface SubjectService {
    void create(CreateSubjectDto dto);
    void assignStudent(Long subjectId, Long studentId);
}
