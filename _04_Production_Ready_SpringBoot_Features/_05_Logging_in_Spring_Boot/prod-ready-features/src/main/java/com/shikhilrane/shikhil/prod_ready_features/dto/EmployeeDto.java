package com.shikhilrane.shikhil.prod_ready_features.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto {
    private Long id;

    private String name;

    private String email;

    private Integer age;

    private String role;

    private Integer salary;

    private Double salaryHike;

    private LocalDate dateofJoining;

    private Boolean isActive;

}
