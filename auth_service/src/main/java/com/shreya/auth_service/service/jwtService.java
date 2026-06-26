package com.shreya.auth_service.service;

import com.shreya.auth_service.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class jwtService {

    private final String SECRET =
            "my-super-secret-key-my-super-secret-key";

    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("tier", user.getTier())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )
                .signWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        )
                )
                .compact();
    }
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(
                        Keys.hmacShaKeyFor(
                                SECRET.getBytes()
                        )
                )
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String extractUsername(String token) {

        return extractAllClaims(token)
                .getSubject();
    }
    public String extractTier(String token) {

        return extractAllClaims(token)
                .get("tier", String.class);
    }
    public Long extractUserId(String token) {

        return extractAllClaims(token)
                .get("userId", Long.class);
    }
    public boolean isValid(String token) {

        try {

            extractAllClaims(token);

            return true;

        } catch (Exception e) {

            return false;
        }
    }

}
