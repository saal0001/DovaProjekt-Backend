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

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));
    }

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

    public void deleteCustomer(UUID customerId) {
        Customer customer = getCustomerById(customerId);
        customerRepository.delete(customer);
    }
}
