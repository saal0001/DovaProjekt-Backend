package com.example.dovaprojektbackend.service;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("dev")
public class DevJwtTokenProvider {

    public boolean validateToken(String token) {
        return true; // alt er gyldigt
    }
    public UUID getSupabaseUserId(String token) {
        return UUID.fromString("00000000-0000-0000-0000-000000000000");
    }

    public String getEmailFromToken(String token) {
        return "test@example.com";
    }

    public String getRoleFromToken(String token) {
        return "USER";
    }
}
