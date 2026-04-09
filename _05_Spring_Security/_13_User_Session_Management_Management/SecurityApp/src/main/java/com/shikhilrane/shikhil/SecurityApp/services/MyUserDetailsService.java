package com.shikhilrane.shikhil.SecurityApp.services;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsService extends UserDetailsService {
    User save(User newUser);
}
