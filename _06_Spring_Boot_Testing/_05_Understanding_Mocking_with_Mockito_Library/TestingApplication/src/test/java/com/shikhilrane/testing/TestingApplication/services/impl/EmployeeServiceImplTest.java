package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.TestContainerConfiguration;
import com.shikhilrane.testing.TestingApplication.dto.EmployeeDto;
import com.shikhilrane.testing.TestingApplication.entities.Employee;
import com.shikhilrane.testing.TestingApplication.repositories.EmployeeRepository;
import com.shikhilrane.testing.TestingApplication.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 3. Tells Spring not to replace the configured database with an in-memory database during tests
@Import(TestContainerConfiguration.class)   // 2. Imports the TestContainerConfiguration class so that the PostgreSQL test container is used during testing
@ExtendWith(MockitoExtension.class)     // 1. Enables Mockito support in JUnit 5 so that @Mock and @InjectMocks annotations work
class EmployeeServiceImplTest {

    @Mock
    private ModelMapper modelMapper;                // 7.6 Create a mock ModelMapper so that real mapping logic is not executed during unit testing

    @Mock
    private EmployeeRepository employeeRepository;  // 5. Creates a mock (fake) object of EmployeeRepository so that the real database is not used

    @InjectMocks
    private EmployeeServiceImpl employeeService;    // 4. Creates an instance of EmployeeServiceImpl and injects the mocked repository into it (@Mock -> @InjectMocks)

    private Employee mockEmployee;                  // 7.4 Declare a mock Employee object which will act as test data

    private EmployeeDto mockEmployeeDto;            // 7.5 Declare a mock EmployeeDto object which represents the DTO returned by the service

    @BeforeEach
    void setUp() {      // 7.1 This method runs before every test case to prepare common test data
        mockEmployee = Employee.builder()   // 7.2 Create a mock Employee object using builder pattern which will be used in test cases
                .id(1L)
                .email("anuj@gmail.com")
                .name("Anuj")
                .salary(200L)
                .build();

        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);     // 7.3 Convert the Employee entity into EmployeeDto using ModelMapper for testing
    }

    // 6. Create a test method
    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_thenReturnEmployeeDto(){
        // Assign
        // 8.1 Prepare the test input and define behaviour of mocked repository
        Long id = 1L;
        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));    // Stubbing
        // Here we stub the repository so that when findById(id) is called, it returns the mockEmployee instead of calling the real database

        // Act
        // 8.2 Call the service method that we want to test
        EmployeeDto employeeDto = employeeService.getEmployeeById(id);  // This executes the actual service logic which internally calls the mocked repository

        // Assert
        // 8.3 Verify that the returned result is correct
        assertThat(employeeDto).isNotNull();                                    // Check that the returned EmployeeDto is not null
        assertThat(employeeDto.getId()).isEqualTo(id);                          // Verify that the returned id matches the expected id
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());  // Verify that the email matches the mock employee email
        verify(employeeRepository, only()).findById(id);                        // Verify that repository.findById(id) was called exactly once

    }
}