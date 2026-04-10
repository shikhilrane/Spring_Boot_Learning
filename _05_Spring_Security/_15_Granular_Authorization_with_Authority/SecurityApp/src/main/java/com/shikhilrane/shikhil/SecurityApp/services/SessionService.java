package com.shikhilrane.shikhil.SecurityApp.services;

import com.shikhilrane.shikhil.SecurityApp.entities.User;

public interface SessionService {
    void generateNewSession(User user, String refreshToken);
    void validateSession(String refreshToken);
    void logout(String refreshToken);
}
