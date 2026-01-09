package com.inputValidation.inputValidationCustomAnnotation.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private Integer age;

    private String role;

    private Integer salary;

    private Double salaryHike;

    private LocalDate dateofJoining;

    @JsonProperty("isActive") // It tells Jackson to map the JSON property "isActive" to the Java field isActive during serialization/deserialization
    private Boolean isActive;


    public void setId(Long id) {    // Lombok may not working properly so created setId setter
        this.id = id;
    }
}

// @Entity -> It will tell DataJPA or hibernate that this is the class that you need to convert into table in Database
// @Table -> Name of table in DB (if we didn't use this annotation then it will create table with name of class)
// @Id -> It marks as ID in table
// @GeneratedValue(strategy = GenerationType.AUTO) -> It will auto generate the id for every row
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor