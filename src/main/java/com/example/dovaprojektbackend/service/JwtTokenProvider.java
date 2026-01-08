package com.example.dovaprojektbackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Profile("prod")
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${supabase.jwt.secret}") String jwtSecret) {
        // Supabase JWT secret fra environment variables
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Validerer JWT token fra Supabase
     * @param token JWT token fra Authorization header
     * @return true hvis token er gyldig, false ellers
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Henter alle claims fra JWT token
     * @param token JWT token
     * @return Claims objekt med alle data fra token
     */
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Henter Supabase user ID (sub claim) fra JWT token
     * @param token JWT token
     * @return UUID af brugerens Supabase ID
     */
    public UUID getSupabaseUserId(String token) {
        Claims claims = getClaims(token);
        String subject = claims.getSubject(); // 'sub' claim indeholder Supabase user ID
        return UUID.fromString(subject);
    }

    /**
     * Henter email fra JWT token
     * @param token JWT token
     * @return brugerens email
     */
    public String getEmailFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }
}