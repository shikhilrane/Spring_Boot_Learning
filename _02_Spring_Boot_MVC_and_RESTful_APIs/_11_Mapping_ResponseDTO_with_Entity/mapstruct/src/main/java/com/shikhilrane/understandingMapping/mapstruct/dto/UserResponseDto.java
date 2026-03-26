package com.shikhilrane.understandingMapping.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String name;
    private String password;
    private String dob;
    private String status;
    private String mob;
    private String emailId;
}
