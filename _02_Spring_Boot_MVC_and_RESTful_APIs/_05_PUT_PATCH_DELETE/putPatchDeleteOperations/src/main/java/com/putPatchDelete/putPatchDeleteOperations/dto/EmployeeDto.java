package com.putPatchDelete.putPatchDeleteOperations.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String name;

    private String email;

    private Integer age;

    private LocalDate dateofJoining;

    @JsonProperty("isActive") // @JsonProperty("isActive") means when JSON has "isActive", it should match with your Java field isActive, even if the names don’t perfectly match or follow different naming rules.
    // It helps connect JSON data to your Java class properly.
    private Boolean isActive;

}
