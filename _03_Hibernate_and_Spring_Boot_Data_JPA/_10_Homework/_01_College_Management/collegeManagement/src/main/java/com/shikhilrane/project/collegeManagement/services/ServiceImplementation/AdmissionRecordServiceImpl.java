package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;


import com.shikhilrane.project.collegeManagement.repositories.AdmissionRecordRepository;
import com.shikhilrane.project.collegeManagement.repositories.StudentRepository;
import com.shikhilrane.project.collegeManagement.services.AdmissionRecordService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionRecordServiceImpl implements AdmissionRecordService {

    private final AdmissionRecordRepository admissionRecordRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


}
