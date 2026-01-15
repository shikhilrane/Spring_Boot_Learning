package com.shikhilrane.projection.learnProjection.entities;

import com.shikhilrane.projection.learnProjection.entities.type.BloodGroupType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    private String email;

    private String gender;

    @Enumerated(value = EnumType.STRING)
    private BloodGroupType bloodGroup;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // One-To-One with Insurance
    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_insurance_id", unique = true)   // Can be used only at owning side (use to rename the name of to mapping in DB table)
    private Insurance insurance;    // Owning Side (this will work as Foreign Key in Patient table so it is a Owning side)

    // One-To-Many with Appointment
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Inverse Side
//    @ToString.Exclude
    private List<Appointment> appointments = new ArrayList<>();
}
