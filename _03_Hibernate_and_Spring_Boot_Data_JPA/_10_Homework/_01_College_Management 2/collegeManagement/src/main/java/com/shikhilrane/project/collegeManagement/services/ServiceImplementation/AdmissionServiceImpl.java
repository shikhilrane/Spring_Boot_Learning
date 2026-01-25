package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.AdmissionResponseDto;
import com.shikhilrane.project.collegeManagement.dto.CreateAdmissionDto;
import com.shikhilrane.project.collegeManagement.entities.AdmissionRecord;
import com.shikhilrane.project.collegeManagement.entities.StudentEntity;
import com.shikhilrane.project.collegeManagement.repositories.AdmissionRecordRepository;
import com.shikhilrane.project.collegeManagement.repositories.StudentRepository;
import com.shikhilrane.project.collegeManagement.services.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRecordRepository admissionRepo;
    private final StudentRepository studentRepo;

    @Override
    public AdmissionResponseDto create(CreateAdmissionDto dto) {
        StudentEntity student = studentRepo.findById(dto.getStudentId()).orElseThrow();

        AdmissionRecord record = new AdmissionRecord();
        record.setFees(dto.getFees());
        record.setStudentEntity(student);

        AdmissionRecord saved = admissionRepo.save(record);

        return new AdmissionResponseDto(
                saved.getId(),
                saved.getFees(),
                student.getId()
        );
    }
}
