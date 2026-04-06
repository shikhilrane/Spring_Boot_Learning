package com.shikhilrane.shikhil.SecurityApp.controllers;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.SignUpDtop;
import com.shikhilrane.shikhil.SecurityApp.dto.UserDto;
import com.shikhilrane.shikhil.SecurityApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDtop signUpDtop){
        UserDto userDto = authService.signUp(signUpDtop);   // Creates a new user account and saves it in the database
        return ResponseEntity.ok(userDto);                  // Returns the created user details in response
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        String token = authService.loginUser(loginDto);     // Authenticates user and generates a JWT token
        Cookie cookie = new Cookie("token", token);   // Cookie with name "token" will be saved in the browser
        cookie.setHttpOnly(true);                           // Makes the cookie inaccessible to JavaScript (security against XSS)
        httpServletResponse.addCookie(cookie);              // Adds the cookie to the HTTP response so browser stores it
        return ResponseEntity.ok(token);                    // Returns the generated JWT token in response body
    }

}
