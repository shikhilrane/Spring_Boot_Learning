package com.shikhilrane.testing.TestingApplication.controllers;

import com.shikhilrane.testing.TestingApplication.entities.Employee;
import com.shikhilrane.testing.TestingApplication.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTestIT extends AbstractIntegrationTest {
    @Autowired
    private EmployeeRepository employeeRepository;  // Used to save or delete employee data in the database before running the API tests

    @BeforeEach
    void setUp() {  // Runs before every test and clears the database so that each test starts with a clean state and previous test data does not affect other tests
        employeeRepository.deleteAll();
    }

    // 1. Test to getEmployeeById, when success
    @Test
    void testGetEmployeeById_success() {
        Employee savedEmployee = employeeRepository.save(testEmployee);                 // First we save a test employee in the database so that the API has data to fetch
        webTestClient.get()                                                             // Send a GET request using WebTestClient
                .uri("/employees/getSingleEmployee/{id}", savedEmployee.getId())   // Call the API endpoint with the saved employee id
                .exchange()                                                             // Execute the HTTP request
                .expectStatus().isOk()                                                  // Verify that the API response status is 200 OK
                .expectBody()                                                           // Start validating the response body returned by the API
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())           // Check that the id returned in the response matches the saved employee id
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());    // Check that the email returned in the response matches the saved employee email
    }
}