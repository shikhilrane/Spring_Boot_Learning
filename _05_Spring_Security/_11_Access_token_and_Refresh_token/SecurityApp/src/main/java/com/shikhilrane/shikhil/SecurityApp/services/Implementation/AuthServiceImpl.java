package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.LoginResponseDto;
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
    private final MyUserDetailsServiceImpl myUserDetailsService;

    @Override
    public UserDto signUp(SignUpDtop signUpDtop) {
        Optional<User> byEmail = userRepository.findByEmail(signUpDtop.getEmail());
        if (byEmail.isPresent()) {
            throw new BadCredentialsException("User with given email " + signUpDtop.getEmail() + " is already exist");  // 4. Check if user already exists with this email
        }

        User toBeCreatedUser = modelMapper.map(signUpDtop, User.class);                     // Convert signup DTO to User entity
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword())); // Encode password before saving
        User saved = userRepository.save(toBeCreatedUser);                                  // Save user in database
        return modelMapper.map(saved, UserDto.class);                                       // Convert saved user entity to UserDto
    }

    @Override
    public LoginResponseDto loginUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())    // Authenticate user with email and password from AM
        );
        User user = (User) authentication.getPrincipal();   // Get authenticated user details
        String accessToken = jwtService.generateAccessToken(user);  // Generate JWT access token for the user
        String refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponseDto(user.getId(), accessToken, refreshToken);                                       // Return generated token
    }

    @Override
    public LoginResponseDto refreshToken(String getFirstRefreshTokenFromArray) {    // Method to generate a new access token using the refresh token
        Long userId = jwtService.getUserIdFromToken(getFirstRefreshTokenFromArray); // Extract userId from the refresh token
        User user = myUserDetailsService.getUserById(userId);                       // Fetch user details from database using the userId
        String accessToken = jwtService.generateAccessToken(user);                  // Generate a new access token for the user
        return new LoginResponseDto(user.getId(), accessToken, getFirstRefreshTokenFromArray); // Return response containing userId, new access token and refresh token
    }
}
