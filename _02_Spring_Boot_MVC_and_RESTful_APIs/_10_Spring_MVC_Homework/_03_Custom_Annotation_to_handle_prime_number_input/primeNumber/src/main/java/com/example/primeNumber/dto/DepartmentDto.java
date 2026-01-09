package com.example.primeNumber.dto;

import com.example.primeNumber.annotations.DepartmentTitleAnnotation;
import com.example.primeNumber.annotations.PrimeNumCheck;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private Long id;

    @NotNull(message = "Title should not be blank")
    @Size(min = 5, max = 10, message = "Please enter valid title")
//    @Pattern(regexp = "^(Admin|Management)$", message = "Role of Employee can either be ADMIN or USER")
    @DepartmentTitleAnnotation
    private String title;

    @PastOrPresent(message = "Put date on Past or Present")
    private LocalDate createdAt;

    @AssertTrue(message = "You should be true")
    @JsonProperty("active")
    private boolean active;

    @PrimeNumCheck
    private int number;
}
