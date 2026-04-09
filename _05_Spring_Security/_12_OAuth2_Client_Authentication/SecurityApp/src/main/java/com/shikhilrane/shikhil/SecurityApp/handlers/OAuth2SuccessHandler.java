package com.shikhilrane.shikhil.SecurityApp.handlers;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.services.Implementation.MyUserDetailsServiceImpl;
import com.shikhilrane.shikhil.SecurityApp.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MyUserDetailsServiceImpl myUserDetailsService;
    private final JwtService jwtService;

    @Value("${env.deploy}")
    private String deployEnv;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. Gets the Google user details (like email and name) after login.
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;   // Cast Authentication object to OAuth2AuthenticationToken (Because Spring gives a generic Authentication object, so we cast it to OAuth2AuthenticationToken to access OAuth2-specific details like the OAuth2 user.)
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();        // Get the authenticated OAuth2 user (Google user details)

        log.info(oAuth2User.getAttribute("email"));                               // Log the email of the authenticated user

        // 2. First it gets the user's email from Google and checks if that user exists in the database. If not, it creates and saves a new user.
        String email = oAuth2User.getAttribute("email");                          // Extract the email attribute from the OAuth2 user object
        User userByEmail = myUserDetailsService.getUserByEmail(email);                  // Check in database if a user with this email already exists
        if (userByEmail == null){                                                       // If user does not exist in database
            User newUser = User.builder()                                               // Create a new User object using builder pattern
                    .name(oAuth2User.getAttribute("name"))                        // Set user's name from Google OAuth2 response
                    .email(oAuth2User.getAttribute("email"))                      // Set user's email from Google OAuth2 response
                    .build();                                                           // Build the User object
            userByEmail = myUserDetailsService.save(newUser);                           // Save the new user into the database and assign it back to userByEmail
        }

        // 3. Then it creates two tokens: an access token and a refresh token.
        String accessToken = jwtService.generateAccessToken(userByEmail); // Generate JWT access token for the authenticated user (short-lived token used for API access)
        String refreshToken = jwtService.generateRefreshToken(userByEmail); // Generate JWT refresh token for the user (long-lived token used to generate new access tokens)

        // 4. The refresh token is stored in a cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);   // Create a cookie to store the refresh token in the browser
        cookie.setHttpOnly(true);                                         // Makes the cookie inaccessible to JavaScript (protects against XSS attacks)
        cookie.setSecure("production".equals(deployEnv));                 // Set cookie to be sent only over HTTPS if the environment is production
        response.addCookie(cookie);                                       // Adds the refresh token cookie to the HTTP response so the browser stores it

        // 5. Creates the frontend URL with the access token and redirects the user’s browser to the frontend page after login.
        String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;  // Prepare redirect URL and attach the access token as query parameter
        getRedirectStrategy().sendRedirect(request,response,frontEndUrl);           // Redirect the user's browser to the frontend page after successful OAuth login
    }
}
