package com.shikhilrane.shikhil.SecurityApp.controllers;

import com.shikhilrane.shikhil.SecurityApp.dto.LoginDto;
import com.shikhilrane.shikhil.SecurityApp.dto.LoginResponseDto;
import com.shikhilrane.shikhil.SecurityApp.dto.SignUpDtop;
import com.shikhilrane.shikhil.SecurityApp.dto.UserDto;
import com.shikhilrane.shikhil.SecurityApp.services.AuthService;
import com.shikhilrane.shikhil.SecurityApp.services.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final SessionService sessionService;

    @Value("${env.deploy}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDtop signUpDtop){
        UserDto userDto = authService.signUp(signUpDtop);   // Creates a new user account and saves it in the database
        return ResponseEntity.ok(userDto);                  // Returns the created user details in response
    }

    @PostMapping("/login")
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
        Cookie[] cookies = httpServletRequest.getCookies();                 // Get all cookies sent by the browser in the request
        String getFirstRefreshTokenFromArray = Arrays.stream(cookies)       // Convert cookies array into a stream to process it
                .filter(c -> "refreshToken".equals(c.getName()))    // Filter the cookie whose name is "refreshToken"
                .findFirst()                                                // Get the first cookie that matches the filter
                .map(c-> c.getValue())                              // Extract the value of the refreshToken cookie
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not found inside the cookie")); // Throw exception if refreshToken cookie is not found
        LoginResponseDto loginResponseDto = authService.refreshToken(getFirstRefreshTokenFromArray);    // Call service to generate a new access token using the refresh token
        return ResponseEntity.ok(loginResponseDto);                         // Return the new access token response with HTTP 200 OK
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue("refreshToken") String refreshToken){
        sessionService.logout(refreshToken);                                // Delete the session associated with this refresh token
        return ResponseEntity.ok("Logged out successfully");          // Return success response
    }
}
