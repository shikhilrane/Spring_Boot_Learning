package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.CreateStudentDto;
import com.shikhilrane.project.collegeManagement.dto.StudentResponseDto;
import com.shikhilrane.project.collegeManagement.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public StudentResponseDto create(@RequestBody CreateStudentDto dto) {
        return studentService.create(dto);
    }

    @PostMapping("/{studentId}/professor/{professorId}")
    public void assignProfessor(@PathVariable Long studentId,
                                @PathVariable Long professorId) {
        studentService.assignProfessor(studentId, professorId);
    }
}
