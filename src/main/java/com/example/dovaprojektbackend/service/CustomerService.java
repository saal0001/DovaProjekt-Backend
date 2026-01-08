package com.example.dovaprojektbackend.service;

import com.example.dovaprojektbackend.model.Customer;
import com.example.dovaprojektbackend.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
    }

    @Transactional
    public Customer updateCustomer(UUID customerId, Customer updatedCustomer) {
        Customer customer = getCustomerById(customerId);

        if (updatedCustomer != null) {
            customer.setName(updatedCustomer.getName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setPhone(updatedCustomer.getPhone());

        }
        return customerRepository.save(customer);
    }

    @Transactional
    public void deleteCustomer(UUID customerId) {
        Customer customer = getCustomerById(customerId);

        if (customer == null) {
            throw new EntityNotFoundException("Customer med id " + customerId + " findes ikke");
        }

        customerRepository.delete(customer);
    }
}
