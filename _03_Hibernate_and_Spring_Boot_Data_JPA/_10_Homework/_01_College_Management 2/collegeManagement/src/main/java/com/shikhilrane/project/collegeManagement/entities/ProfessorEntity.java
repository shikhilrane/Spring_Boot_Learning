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
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String title;

    // Professor with Student (Inverse Side). {Many to Many}
    @ManyToMany(mappedBy = "professorEntity")
    private List<StudentEntity> studentEntity = new ArrayList<>();

    // Professor with Subject (Inverse Side). {One to Many}
    @OneToMany(mappedBy = "professorEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SubjectEntity> subjectEntity = new ArrayList<>();
}