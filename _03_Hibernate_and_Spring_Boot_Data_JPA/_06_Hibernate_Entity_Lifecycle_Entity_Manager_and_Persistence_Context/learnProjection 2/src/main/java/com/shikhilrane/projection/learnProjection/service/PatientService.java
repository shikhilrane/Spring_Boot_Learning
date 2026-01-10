package com.shikhilrane.projection.learnProjection.service;

import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;

    @Transactional
    public void testPatientTransaction(){
        Patient patient1 = patientRepository.findById(1L).orElseThrow();
        Patient patient2 = patientRepository.findById(1L).orElseThrow();

        System.out.println(patient1 + " " + patient2);
        System.out.println(patient1 == patient2);
    }
}
