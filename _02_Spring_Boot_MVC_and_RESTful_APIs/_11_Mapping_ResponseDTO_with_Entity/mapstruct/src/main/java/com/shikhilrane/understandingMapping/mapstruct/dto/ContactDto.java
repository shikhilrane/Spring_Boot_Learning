package com.shikhilrane.understandingMapping.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {
    private Long user_id;
    private String mobileNumber;
    private String email;
}
