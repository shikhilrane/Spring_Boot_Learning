package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.CreateSubjectDto;
import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import com.shikhilrane.project.collegeManagement.entities.StudentEntity;
import com.shikhilrane.project.collegeManagement.entities.SubjectEntity;
import com.shikhilrane.project.collegeManagement.repositories.ProfessorRepository;
import com.shikhilrane.project.collegeManagement.repositories.StudentRepository;
import com.shikhilrane.project.collegeManagement.repositories.SubjectRepository;
import com.shikhilrane.project.collegeManagement.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepo;
    private final ProfessorRepository professorRepo;
    private final StudentRepository studentRepo;

    @Override
    public void create(CreateSubjectDto dto) {
        ProfessorEntity professor = professorRepo.findById(dto.getProfessorId()).orElseThrow();

        SubjectEntity subject = new SubjectEntity();
        subject.setTitle(dto.getTitle());
        subject.setProfessorEntity(professor);

        subjectRepo.save(subject); // owning side save
    }

    // Owning side → Subject
    @Override
    public void assignStudent(Long subjectId, Long studentId) {
        SubjectEntity subject = subjectRepo.findById(subjectId).orElseThrow();
        StudentEntity student = studentRepo.findById(studentId).orElseThrow();

        subject.getStudentEntity().add(student);
        subjectRepo.save(subject);
    }
}
