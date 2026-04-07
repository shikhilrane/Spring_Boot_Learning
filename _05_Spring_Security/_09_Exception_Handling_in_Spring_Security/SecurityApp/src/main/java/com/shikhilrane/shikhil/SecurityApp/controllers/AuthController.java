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

    @PostMapping("/signup")     // 1.
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDtop signUpDtop){      // 2.
        UserDto userDto = authService.signUp(signUpDtop);   // 3. Creates a new user account and saves it in the database
        return ResponseEntity.ok(userDto);                  // Returns the created user details in response
    }

    @PostMapping("/login")      // 10.
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){    // 11. 12.
        String token = authService.loginUser(loginDto);     // 13. Authenticates user and generates a JWT token
        Cookie cookie = new Cookie("token", token);   // 14. Cookie with name "token" will be saved in the browser
        cookie.setHttpOnly(true);                           // 15. Makes the cookie inaccessible to JavaScript (security against XSS)
        httpServletResponse.addCookie(cookie);              // 16. Adds the cookie to the HTTP response so browser stores it
        return ResponseEntity.ok(token);                    // Returns the generated JWT token in response body
    }

}
