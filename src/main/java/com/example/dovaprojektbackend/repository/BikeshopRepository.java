package com.example.dovaprojektbackend.repository;

import com.example.dovaprojektbackend.model.Bikeshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeshopRepository extends JpaRepository<Bikeshop, Long> {
    Optional<Bikeshop> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCrNumber(Integer crNumber);
}
