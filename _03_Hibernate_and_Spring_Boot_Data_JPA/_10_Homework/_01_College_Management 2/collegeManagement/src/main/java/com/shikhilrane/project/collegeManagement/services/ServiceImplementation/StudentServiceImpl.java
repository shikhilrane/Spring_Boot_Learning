package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.CreateStudentDto;
import com.shikhilrane.project.collegeManagement.dto.StudentResponseDto;
import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import com.shikhilrane.project.collegeManagement.entities.StudentEntity;
import com.shikhilrane.project.collegeManagement.repositories.ProfessorRepository;
import com.shikhilrane.project.collegeManagement.repositories.StudentRepository;
import com.shikhilrane.project.collegeManagement.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;
    private final ProfessorRepository professorRepo;

    @Override
    public StudentResponseDto create(CreateStudentDto dto) {
        StudentEntity s = new StudentEntity();
        s.setName(dto.getName());
        StudentEntity saved = studentRepo.save(s);

        return new StudentResponseDto(saved.getId(), saved.getName());
    }

    // Owning side → Student
    @Override
    public void assignProfessor(Long studentId, Long professorId) {
        StudentEntity student = studentRepo.findById(studentId).orElseThrow();
        ProfessorEntity professor = professorRepo.findById(professorId).orElseThrow();

        student.getProfessorEntity().add(professor);
        studentRepo.save(student);
    }
}
