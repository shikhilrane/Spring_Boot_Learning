package com.shikhilrane.understandingMapping.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
    private Long id;
    private String name;
    private String password;
    private String dob;
    private String status;
}
