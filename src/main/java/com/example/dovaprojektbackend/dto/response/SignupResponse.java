package com.example.dovaprojektbackend.dto.response;

import com.example.dovaprojektbackend.model.enums.Role;

import java.util.UUID;

public class SignupResponse {
    private UUID userId;
    private String email;
    private Role role;
    private String message;

    public SignupResponse() {}

    public SignupResponse(UUID userId, String email, Role role, String message) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
