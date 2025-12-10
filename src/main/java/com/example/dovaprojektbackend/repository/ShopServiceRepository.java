package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.ShopService;

public interface ShopServiceRepository extends JpaRepository<ShopService, Integer> {
    
}
