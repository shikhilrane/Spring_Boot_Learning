package com.shikhilrane.projection.learnProjection.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(nullable = false)   // Owning side
    @ToString.Exclude
    @JsonIgnore     // tells Jackson to skip that field while converting an object to JSON, preventing recursion and unwanted data in API responses.
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @ToString.Exclude
    @JsonIgnore     // tells Jackson to skip that field while converting an object to JSON, preventing recursion and unwanted data in API responses.
    private Doctor doctor;
}
