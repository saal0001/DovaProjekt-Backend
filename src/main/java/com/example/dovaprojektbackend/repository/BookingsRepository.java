package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.Booking;

import java.util.UUID;

public interface BookingsRepository extends JpaRepository<Booking, UUID> {
    
}
