package com.shikhilrane.shikhil.SecurityApp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long sessionId;             // Unique identifier for each session

    @Column
    private String refreshToken;        // Stores the refresh token associated with this session

    @Column
    @CreationTimestamp
    private LocalDateTime lastUsedAt;   // Stores the last time this session was used

    @ManyToOne
    private User user;                  // Reference to the user who owns this session
}
