package com.shikhilrane.shikhil.SecurityApp.services;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.LoginResponseDto;
import com.shikhilrane.shikhil.SecurityApp.dto.SignUpDtop;
import com.shikhilrane.shikhil.SecurityApp.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService{
    UserDto signUp(SignUpDtop signUpDtop);

    LoginResponseDto loginUser(LoginDto loginDto);

    LoginResponseDto refreshToken(String getFirstRefreshTokenFromArray);
}
