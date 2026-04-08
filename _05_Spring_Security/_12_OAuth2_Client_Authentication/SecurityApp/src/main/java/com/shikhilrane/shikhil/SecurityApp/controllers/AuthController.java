package com.shikhilrane.shikhil.SecurityApp.controllers;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.LoginResponseDto;
import com.shikhilrane.shikhil.SecurityApp.dto.SignUpDtop;
import com.shikhilrane.shikhil.SecurityApp.dto.UserDto;
import com.shikhilrane.shikhil.SecurityApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Value("${env.deploy}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDtop signUpDtop){
        UserDto userDto = authService.signUp(signUpDtop);   // Creates a new user account and saves it in the database
        return ResponseEntity.ok(userDto);                  // Returns the created user details in response
    }

    @PostMapping("/login")      // 10.
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        LoginResponseDto loginResponseDto = authService.loginUser(loginDto);     // Authenticates the user and generates Access Token + Refresh Token
        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());   // Create a cookie to store the refresh token in the browser
        cookie.setHttpOnly(true);                           // Makes the cookie inaccessible to JavaScript (protects against XSS attacks)
        cookie.setSecure("production".equals(deployEnv));
        httpServletResponse.addCookie(cookie);              // Adds the refresh token cookie to the HTTP response so the browser stores it
        return ResponseEntity.ok(loginResponseDto);         // Returns the login response (usually containing the access token) in the response body
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        String getFirstRefreshTokenFromArray = Arrays.stream(cookies)
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(c-> c.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the cookie"));
        LoginResponseDto loginResponseDto = authService.refreshToken(getFirstRefreshTokenFromArray);
        return ResponseEntity.ok(loginResponseDto);
    }
}
