package com.shikhilrane.shikhil.SecurityApp.services.Implementation;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;    // Create a Minimum 256 bits (32 characters) secret key for HS256 in application.properties / env variable.

    private SecretKey getSecretKey(){   // Encode secret key with HS256 algorithm
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(User user) {
        return Jwts.builder()                                   // builder() to create the token
                .subject(user.getId().toString())               // subject would be our id (converted to string because it was in Long)
                .claim("email", user.getEmail())             // We can add as many as claim, claims as per our entity class
                .claim("roles", Set.of("ADMIN", "USER"))     // Hardcoded roles (hardcoding is not ideal and we will see this in authorization part)
                .issuedAt(new Date())                           // Issued as current time
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10)) // Expire token from current time to 60 seconds * 10  (i.e. after 10 minute)
                .signWith(getSecretKey())                       // Sign a secret key
                .compact();                                     // Create a Token
    }

    @Override
    public String generateRefreshToken(User user) {
        return Jwts.builder()                                   // builder() to create the token
                .subject(user.getId().toString())               // subject would be our id (converted to string because it was in Long)
                .issuedAt(new Date())                           // Issued as current time
                .expiration(new Date(System.currentTimeMillis() + (1000L *60*60*24*30*6))) // Expire Refresh token from current time to 6 months (i.e. after 6 Months)
                .signWith(getSecretKey())                       // Sign a secret key
                .compact();                                     // Create a Token
    }

    @Override
    public Long getUserIdFromToken(String token) {          // Method to get user id from generated token
        Claims claims = Jwts.parser()                       // .parser() to get data from token
                .verifyWith(getSecretKey())                 // We are telling to verify this token from this secret key
                .build()
                .parseSignedClaims(token)                   // pass only the JWT token created with generateUserToken()
                .getPayload();                              // it includes subject, claims, issuedAt, expiration from token
        return Long.valueOf(claims.getSubject());
    }
}
