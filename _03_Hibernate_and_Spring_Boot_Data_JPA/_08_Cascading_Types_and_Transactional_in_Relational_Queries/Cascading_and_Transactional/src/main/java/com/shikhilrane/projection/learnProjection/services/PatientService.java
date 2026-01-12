package com.shikhilrane.projection.learnProjection.services;

import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.InsuranceRepository;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public void deletePatient(Long patientId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();
        patientRepository.deleteById(patientId);
    }
}
