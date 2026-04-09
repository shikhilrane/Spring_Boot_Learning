package com.shikhilrane.shikhil.SecurityApp.services;

import com.shikhilrane.shikhil.SecurityApp.entities.User;

public interface JwtService {
    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    Long getUserIdFromToken(String token);
}
