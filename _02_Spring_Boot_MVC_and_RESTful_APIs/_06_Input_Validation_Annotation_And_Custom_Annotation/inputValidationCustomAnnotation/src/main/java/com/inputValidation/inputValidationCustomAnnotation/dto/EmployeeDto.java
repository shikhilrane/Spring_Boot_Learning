package com.inputValidation.inputValidationCustomAnnotation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inputValidation.inputValidationCustomAnnotation.annotations.AgeValidationAnnotation;
import com.inputValidation.inputValidationCustomAnnotation.annotations.EmployeeRoleValidationAnnotation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotNull(message = "Name is required")      // Neither even declared nor stated
    @NotEmpty(message = "Name can't be empty")  // Declared but value not stated
    @NotBlank(message = "Please don't use spaces in name")  // Mostly used in Strings (doesn't entertain spaces)
    @Size(min = 2, max = 10, message = "Number of characters should be in the range from 2 to 10")  // Mostly used in Strings or Array
    private String name;

    @NotBlank(message = "Email of the employee can't be blank")
    @Email(message = "Email should be valid email") // Use to validate email
    private String email;

//    @Max(value = 80, message = "Age can't be more than 80")     // Use with numbers (Commented because we have to use Custom Annotations)
//    @Min(value = 18, message = "Age can't be lesser than 18")   // Use with numbers
    @AgeValidationAnnotation
    private Integer age;

    @NotBlank(message = "Role of the employee can't be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can either be ADMIN or USER") // Commented because we have to use Custom Annotations
    @EmployeeRoleValidationAnnotation
    private String role;    // ADMIN, USER (This string can take these 2 values and not any other values)

    @NotNull(message = "Salary of employee should not be null")
    @Positive(message = "Salary of employee should be positive")
    private Integer salary;

    @Digits(integer = 3, fraction = 2)  // Maximum 3 integers and 2 fractions
    @DecimalMin(value = "00.1")
    @DecimalMax(value = "100.00")
    private Double salaryHike;

    @PastOrPresent(message = "Date of joining should be in Past or in Present only")
    private LocalDate dateofJoining;

    @JsonProperty("isActive") // @JsonProperty("isActive") means when JSON has "isActive", it should match with your Java field isActive, even if the names don’t perfectly match or follow different naming rules.
    // It helps connect JSON data to your Java class properly.
    @AssertTrue(message = "Employee should be Active")
    private Boolean isActive;

}
