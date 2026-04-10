package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.entities.Session;
import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.repositories.SessionRepository;
import com.shikhilrane.shikhil.SecurityApp.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;                        // Maximum number of active sessions allowed per user

    // Method to create a new session when user logs in
    @Override
    public void generateNewSession(User user, String refreshToken) {
        // Fetching number of active sessions for a user
        List<Session> userSession = sessionRepository.findByUser(user);

        // Check if the user already reached the maximum allowed sessions
        if (userSession.size()==SESSION_LIMIT) {
            userSession.sort(Comparator.comparing(s -> s.getLastUsedAt()));     // Sort sessions by last used time (oldest first)
            Session leastRecentlyUsedSession = userSession.getFirst();                  // Get the least recently used session
            sessionRepository.delete(leastRecentlyUsedSession);                         // Delete the oldest session to free space for new session
        }

        // If session limit is not exceeded, then create new session for user
        Session newSession = Session.builder()      // Create a new Session object
                .user(user)                         // Set the user for this session
                .refreshToken(refreshToken)         // Store refresh token in the session
                .build();                           // Build the session object
        sessionRepository.save(newSession);         // Save the new session in the database
    }

    // Method to validate session during refresh token request
    @Override
    public void validateSession(String refreshToken) {
        Session session = sessionRepository.findByRefreshToken(refreshToken)        // Find session using refresh token
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken : " + refreshToken)); // Throw exception if session does not exist
        session.setLastUsedAt(LocalDateTime.now());                                 // Update last used time to current time
        sessionRepository.save(session);                                            // Save updated session back to database
    }

    // Method to logout user by removing their session
    @Override
    public void logout(String refreshToken){
        sessionRepository.findByRefreshToken(refreshToken)                  // Find session using refresh token
                .ifPresent(s -> sessionRepository.delete(s));       // If session exists, delete it from database
    }
}
