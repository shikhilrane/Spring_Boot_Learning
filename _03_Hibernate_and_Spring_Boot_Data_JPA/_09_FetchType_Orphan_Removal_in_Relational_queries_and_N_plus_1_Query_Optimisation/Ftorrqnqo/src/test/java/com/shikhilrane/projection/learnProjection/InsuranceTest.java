package com.shikhilrane.projection.learnProjection;

import com.shikhilrane.projection.learnProjection.entities.Appointment;
import com.shikhilrane.projection.learnProjection.entities.Insurance;
import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.services.AppointmentService;
import com.shikhilrane.projection.learnProjection.services.InsuranceService;
import com.shikhilrane.projection.learnProjection.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTest {
    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Test
    public void testAssignInsuranceToPatient(){
        Insurance insurance = Insurance.builder()
                .provider("HDFC Ergo")
                .policyNumber("HDFC_23G")
                .validUntil(LocalDate.of(2030, 1,1))
                .build();

        Insurance insurance1 = insuranceService.assignInsuranceToPatient(insurance, 1L);
        System.out.println(insurance1);

//        patientService.deletePatient(1L);

        Patient patient = insuranceService.removeInsuranceToPatient(1L);
        System.out.println(patient);
    }

    @Test
    public void testCreateInAppointment(){
        Appointment appointment = Appointment.builder()
                .appointmentTime(LocalDateTime.of(2025, 11, 1,14,0,0))
                .reason("Cancer")
                .build();

        Appointment appointment1 = appointmentService.createNewAppointment(appointment, 1L, 2L);

        System.out.println(appointment1);

        patientService.deletePatient(1L);
    }

}
