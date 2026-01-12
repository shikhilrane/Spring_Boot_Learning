package com.shikhilrane.projection.learnProjection.repositories;

import com.shikhilrane.projection.learnProjection.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}