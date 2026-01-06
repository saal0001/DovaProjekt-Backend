package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(UUID userId){
        return customerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public Customer updateCustomer(UUID userId,Customer updatedCustomer){
        Customer customer = getCustomerById(userId);

        // Opdater felter
        if (updatedCustomer.getName() != null) {
            customer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            customer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPhone() != null) {
            customer.setPhone(updatedCustomer.getPhone());
        }
        if (updatedCustomer.getAddress() != null) {
            customer.setAddress(updatedCustomer.getAddress());
        }

        return customerRepository.save(customer);
    }

    public void deleteCustomer(UUID userId){
        Customer customer = getCustomerById(userId);
        customerRepository.delete(customer);
    }
}
