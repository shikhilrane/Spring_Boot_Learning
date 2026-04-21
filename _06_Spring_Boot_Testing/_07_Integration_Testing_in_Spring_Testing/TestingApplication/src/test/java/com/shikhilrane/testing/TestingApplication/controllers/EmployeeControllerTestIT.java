package com.shikhilrane.testing.TestingApplication.controllers;

import com.shikhilrane.testing.TestingApplication.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTestIT extends AbstractIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;  // Used to save or delete employee data in the database before running the API tests

    
}