package com.shikhilrane.projection.learnProjection;

import com.shikhilrane.projection.learnProjection.dto.BloodGroupStats;
import com.shikhilrane.projection.learnProjection.dto.CPatientInfo;
import com.shikhilrane.projection.learnProjection.dto.IPatientInfo;
import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientRepository patientRepository;

    // Retrieving all Patient's all info
    @Test
    public void testPatient(){
        List<Patient> patientList = patientRepository.findAll();
        for (Patient i : patientList){
            System.out.println(i);
        }
    }

    // Return All Patient's id, name and email only from interface (Unmodifiable or Read-only)
    @Test
    public void getAllInfoI(){
        List<IPatientInfo> allPatientsInfo = patientRepository.getAllPatientsInfo();
        for (IPatientInfo p : allPatientsInfo) {
            System.out.println( "ID=" + p.getId() + ", Name=" + p.getName() + ", Email=" + p.getEmail());
        }
    }

    // Return All Patient's id and name only from Class (Modifiable)
    @Test
    public void getAllInfoC(){
        List<CPatientInfo> allPatientsInfoConcrete = patientRepository.getAllPatientsInfoConcrete();
        for (CPatientInfo p : allPatientsInfoConcrete){
            System.out.println(p);
        }
    }

    @Test
    public void getCountOfBloodGrp(){
        List<BloodGroupStats> countOfBloodGroupPeople = patientRepository.getCountOfBloodGroupPeople();
        for (BloodGroupStats p : countOfBloodGroupPeople){
            System.out.println(p);
        }
    }

    @Test
    @Transactional
    public void updatePatient(){
        int updated = patientRepository.updatePatientNameWithId("Ishant Sharma", 4L);
        System.out.println("Number of Rows affected : " + updated);
    }

    @Test
    public void nPlusOneQ(){
//        List<Patient> patientList = patientRepository.findAll();
//        for (var i : patientList) {
//            System.out.println(i);
//        }

        List<Patient> allPatientsWithAppointments = patientRepository.getAllPatientsWithAppointments();
        for (var i : allPatientsWithAppointments){
            System.out.println(i);
        }
    }
}
