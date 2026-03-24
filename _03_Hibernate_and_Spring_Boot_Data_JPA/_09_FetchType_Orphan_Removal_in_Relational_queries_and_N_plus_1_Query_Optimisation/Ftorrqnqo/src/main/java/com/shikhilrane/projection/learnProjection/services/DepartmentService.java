package com.shikhilrane.projection.learnProjection.services;

import com.shikhilrane.projection.learnProjection.entities.Department;
import com.shikhilrane.projection.learnProjection.entities.Doctor;
import com.shikhilrane.projection.learnProjection.repositories.DepartmentRepository;
import com.shikhilrane.projection.learnProjection.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public Department createDepartment(Department department, Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();
        department.setHeadDoctor(doctor);
        departmentRepository.save(department);
        return department;
    }

    @Transactional
    public void deleteDeptartment(Long id){
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        doctor.setDepartment(null);
    }

    @Transactional
    public Department assignDepartmentsToDoctors(Long deptId, Long doctorId){
        Department department = departmentRepository.findById(deptId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        department.getDoctors().add(doctor);
        doctor.getDepartments().add(department);

        departmentRepository.save(department);

        return department;
    }
}