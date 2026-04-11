package com.shikhilrane.shikhil.SecurityApp.repositories;

import com.shikhilrane.shikhil.SecurityApp.entities.Session;
import com.shikhilrane.shikhil.SecurityApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUser(User user);                            // Fetch all sessions from database that belong to the given user
    Optional<Session> findByRefreshToken(String refreshToken);      // Find a session using refresh token if it exists
}
