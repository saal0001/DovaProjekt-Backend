package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    
}
