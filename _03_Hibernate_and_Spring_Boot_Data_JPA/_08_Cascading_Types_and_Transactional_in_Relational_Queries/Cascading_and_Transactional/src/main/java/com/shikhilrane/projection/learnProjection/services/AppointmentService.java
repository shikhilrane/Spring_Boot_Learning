package com.shikhilrane.projection.learnProjection.services;

import com.shikhilrane.projection.learnProjection.entities.Appointment;
import com.shikhilrane.projection.learnProjection.entities.Doctor;
import com.shikhilrane.projection.learnProjection.entities.Patient;
import com.shikhilrane.projection.learnProjection.repositories.AppointmentRepository;
import com.shikhilrane.projection.learnProjection.repositories.DoctorRepository;
import com.shikhilrane.projection.learnProjection.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;  // add AppointmentRepository, So that we can fetch data of patient from AppointmentRepository
    private final DoctorRepository doctorRepository;            // add DoctorRepository, So that we can fetch data of patient from DoctorRepository
    private final PatientRepository patientRepository;          // add PatientRepository, So that we can fetch data of patient from PatientRepository

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long patientId, Long doctorId){
        Patient patient = patientRepository.findById(patientId).orElseThrow();  // Find the patientId first or throw not found
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();      // Find the doctorId first or throw not found

        appointment.setPatient(patient);        // If patient found then set patient in appointment
        appointment.setDoctor(doctor);          // If doctor found then set patient in appointment

        appointmentRepository.save(appointment);    // Save their appointment in AppointmentRepository

        return appointment;     // Return the Appointment
    }
}
