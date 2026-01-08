package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.ShopService;

import java.util.List;
import java.util.UUID;

public interface ShopServiceRepository extends JpaRepository<ShopService, UUID> {
    List<ShopService> findByBikeshop_ShopId(UUID shopId);
}
