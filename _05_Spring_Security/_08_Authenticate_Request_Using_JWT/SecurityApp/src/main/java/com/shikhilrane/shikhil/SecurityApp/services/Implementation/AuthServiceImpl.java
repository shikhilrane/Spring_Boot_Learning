package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.SignUpDtop;
import com.shikhilrane.shikhil.SecurityApp.dto.UserDto;
import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.repositories.UserRepository;
import com.shikhilrane.shikhil.SecurityApp.services.AuthService;
import com.shikhilrane.shikhil.SecurityApp.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public UserDto signUp(SignUpDtop signUpDtop) {
        Optional<User> byEmail = userRepository.findByEmail(signUpDtop.getEmail());
        if (byEmail.isPresent()) {
            throw new BadCredentialsException("User with given email " + signUpDtop.getEmail() + " is already exist");  // 4. Check if user already exists with this email
        }

        User toBeCreatedUser = modelMapper.map(signUpDtop, User.class);                     // 5. Convert signup DTO to User entity
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword())); // 6. Encode password before saving
        User saved = userRepository.save(toBeCreatedUser);                                  // 7. Save user in database
        return modelMapper.map(saved, UserDto.class);                                       // 8. Convert saved user entity to UserDto
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())    // 18. Authenticate user with email and password from AM
        );
        User user = (User) authentication.getPrincipal();   // 19. Get authenticated user details
        String token = jwtService.generateUserToken(user);  // 20. Generate JWT token for the user
        return token;                                       // 21. Return generated token
    }
}
