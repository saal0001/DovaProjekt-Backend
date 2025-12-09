package com.example.dovaprojektbackend.repository;

import com.example.dovaprojektbackend.model.Bikeshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BikeshopRepository extends JpaRepository<Bikeshop, UUID> {
    Optional<Bikeshop> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCvrNumber(Integer cvrNumber);
    boolean existsById(UUID shopId);
}
