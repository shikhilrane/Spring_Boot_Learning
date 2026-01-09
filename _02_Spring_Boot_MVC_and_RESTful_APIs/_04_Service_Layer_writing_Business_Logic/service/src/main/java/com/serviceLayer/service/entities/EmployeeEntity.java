package com.serviceLayer.service.entities;

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
    private LocalDate dateofJoining;
    private Boolean isActive;


}

// @Entity -> It will tell DataJPA or hibernate that this is the class that you need to convert into table in Database
// @Table -> Name of table in DB (if we didn't use this annotation then it will create table with name of class)
// @Id -> It marks as ID in table
// @GeneratedValue(strategy = GenerationType.AUTO) -> It will auto generate the id for every row
// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor