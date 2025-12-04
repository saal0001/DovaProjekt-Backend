package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.Ydelse;

public interface YdelseRepository extends JpaRepository<Ydelse, Integer> {
    
}
