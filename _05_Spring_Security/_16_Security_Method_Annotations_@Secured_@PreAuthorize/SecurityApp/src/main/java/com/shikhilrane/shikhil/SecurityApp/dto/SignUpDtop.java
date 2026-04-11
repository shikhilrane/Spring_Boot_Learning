package com.shikhilrane.shikhil.SecurityApp.dto;

import com.shikhilrane.shikhil.SecurityApp.entities.enums.Permission;
import com.shikhilrane.shikhil.SecurityApp.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDtop {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
    private List<Permission> permissions;
}
