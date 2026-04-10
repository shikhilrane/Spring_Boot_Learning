package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.exceptions.ResourceNotFoundException;
import com.shikhilrane.shikhil.SecurityApp.repositories.UserRepository;
import com.shikhilrane.shikhil.SecurityApp.services.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final UserRepository userRepository;

    // This method is for login using JWT
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("User with entered email " + username + " not found"));
    }

    // This method is for Custom Filter for authentication
    public User getUserById(Long userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with entered email " + userId + " not found"));
    }

    // This method us for to get email id of user for OAuth2 in OAuth2SuccessHandler
    public User getUserByEmail(String email){
        return userRepository
                .findByEmail(email)
                .orElse(null);
    }

    @Override
    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
