package com.shikhilrane.projection.learnProjection.services;

import com.shikhilrane.projection.learnProjection.entities.Insurance;
import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final PatientRepository patientRepository;  // add PatientRepository, So that we can fetch data of patient from PatientRepository

    @Transactional // Its important to have @Transactional, if we don't have every fetching and saving opn will have their own persistence context but we add then every operation from given method will happen in single persistence context. Also, if some operation fails then this will roll the method
    public Insurance assignInsuranceToPatient(Insurance insurance, Long patientId){ // Created Insurance will be assigned to entered patientId
        Patient patient = patientRepository.findById(patientId).orElseThrow();      // Find the patientId first or throw not found
        patient.setInsurance(insurance);                                            // If patientId matches then Insurance will be assign to that patientId
        insurance.setPatient(patient);                                              // Optional but good for bidirectional mapping consistency
        return insurance;                                                           // It will return the insurance according to CascadeType
    }

    public Insurance updateInsuranceToPatient(Insurance insurance, Long patientId){ // Created Insurance will be assigned to entered patientId
        Patient patient = patientRepository.findById(patientId).orElseThrow();      // Find the patientId first or throw not found
        patient.setInsurance(insurance);                                            // If patientId matches then Insurance will be assign to that patientId
        insurance.setPatient(patient);                                              // Optional but good for bidirectional mapping consistency
        return insurance;                                                           // It will return the insurance according to CascadeType
    }

    public Patient removeInsuranceToPatient(Long patientId){ // Created Insurance will be assigned to entered patientId
        Patient patient = patientRepository.findById(patientId).orElseThrow();      // Find the patientId first or throw not found
        patient.setInsurance(null);                                            // If patientId matches then Insurance will be assign to that patientId

        return patient;
    }
}
