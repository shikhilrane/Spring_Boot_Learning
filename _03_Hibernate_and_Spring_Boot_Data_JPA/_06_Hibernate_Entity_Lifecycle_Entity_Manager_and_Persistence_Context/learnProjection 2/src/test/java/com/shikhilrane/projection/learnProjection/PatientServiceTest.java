package com.shikhilrane.projection.learnProjection;

import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import com.shikhilrane.projection.learnProjection.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    // Retrieving all Patient's all info
    @Test
    public void testPatient(){
        Patient p1 = new Patient();     // Transient State i.e. Not in Hibernate and not in DB
        patientRepository.save(p1);     // It will call persist method to save object in DB. Persistent i.e. track by Hibernate and Saved in DB
    }

    @Test
    public void  patientTransactionFromService(){
        patientService.testPatientTransaction();
    }
}
