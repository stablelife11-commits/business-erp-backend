package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Customer;
import com.student.studentmanagementapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {

        if (customerRepository.existsByMobile(customer.getMobile())) {
            throw new RuntimeException("Mobile number already exists");
        }

        return customerRepository.save(customer);
    }
}