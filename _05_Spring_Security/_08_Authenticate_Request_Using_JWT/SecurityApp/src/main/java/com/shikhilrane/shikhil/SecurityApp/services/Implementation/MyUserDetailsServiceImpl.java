package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.exceptions.ResourceNotFoundException;
import com.shikhilrane.shikhil.SecurityApp.repositories.UserRepository;
import com.shikhilrane.shikhil.SecurityApp.services.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with entered email " + username + " not found"));
    }

    public User getUserById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with entered email " + userId + " not found"));
    }
}
