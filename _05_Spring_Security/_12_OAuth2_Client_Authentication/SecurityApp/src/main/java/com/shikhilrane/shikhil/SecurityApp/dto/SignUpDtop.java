package com.shikhilrane.shikhil.SecurityApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDtop {
    private String email;
    private String password;
    private String name;
}
