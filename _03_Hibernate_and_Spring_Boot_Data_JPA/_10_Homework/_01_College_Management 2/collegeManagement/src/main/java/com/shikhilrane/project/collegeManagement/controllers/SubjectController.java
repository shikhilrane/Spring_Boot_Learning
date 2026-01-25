package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.CreateSubjectDto;
import com.shikhilrane.project.collegeManagement.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public void create(@RequestBody CreateSubjectDto dto) {
        subjectService.create(dto);
    }

    @PostMapping("/{subjectId}/student/{studentId}")
    public void assignStudent(@PathVariable Long subjectId,
                              @PathVariable Long studentId) {
        subjectService.assignStudent(subjectId, studentId);
    }
}
