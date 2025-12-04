package com.example.dovaprojektbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dovaprojektbackend.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    
}
