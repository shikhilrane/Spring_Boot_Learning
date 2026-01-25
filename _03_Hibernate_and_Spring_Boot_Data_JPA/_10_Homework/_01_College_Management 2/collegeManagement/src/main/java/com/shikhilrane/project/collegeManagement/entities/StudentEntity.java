package com.shikhilrane.project.collegeManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    // Student with Professor (Owning Side). {Many to Many}
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_professor",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private List<ProfessorEntity> professorEntity = new ArrayList<>();

    // Student with Subject (Inverse Side). {Many to Many}
    @ManyToMany(mappedBy = "studentEntity", fetch = FetchType.LAZY)
    private List<SubjectEntity> subjectEntity = new ArrayList<>();

    // Student with AdmissionRecord (Inverse Side). {One to One}
    @OneToOne(mappedBy = "studentEntity", fetch = FetchType.LAZY)
    private AdmissionRecord admissionRecord;
}
