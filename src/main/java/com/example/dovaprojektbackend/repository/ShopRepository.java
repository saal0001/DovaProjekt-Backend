package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    
}
