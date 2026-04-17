package com.shikhilrane.testing.TestingApplication.services.impl;

import com.shikhilrane.testing.TestingApplication.TestContainerConfiguration;
import com.shikhilrane.testing.TestingApplication.dto.EmployeeDto;
import com.shikhilrane.testing.TestingApplication.entities.Employee;
import com.shikhilrane.testing.TestingApplication.repositories.EmployeeRepository;
import com.shikhilrane.testing.TestingApplication.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 3. Tells Spring not to replace the configured database with an in-memory database during tests
@Import(TestContainerConfiguration.class)   // 2. Imports the TestContainerConfiguration class so that the PostgreSQL test container is used during testing
@ExtendWith(MockitoExtension.class)     // 1. Enables Mockito support in JUnit 5 so that @Mock and @InjectMocks annotations work
class EmployeeServiceImplTest {

    @Spy                                            // Using Spy because ModelMapper is already tested somewhere so we just want to use it as Spy
    private ModelMapper modelMapper;                // 7.6 Create a mock ModelMapper so that real mapping logic is not executed during unit testing

    @Mock
    private EmployeeRepository employeeRepository;  // 5. Creates a mock (fake) object of EmployeeRepository so that the real database is not used

    @InjectMocks
    private EmployeeServiceImpl employeeService;    // 4. Creates an instance of EmployeeServiceImpl and injects the mocked repository into it (@Mock -> @InjectMocks)

    private Employee mockEmployee;                  // 7.4 Declare a mock Employee object which will act as test data

    private EmployeeDto mockEmployeeDto;            // 7.5 Declare a mock EmployeeDto object which represents the DTO returned by the service

    @BeforeEach
    void setUp() {                                  // 7.1 This method runs before every test case to prepare common test data
        mockEmployee = Employee.builder()           // 7.2 Create a mock Employee object using builder pattern which will be used in test cases
                .id(1L)
                .email("shikhil@gmail.com")
                .name("Shikhil")
                .salary(200L)
                .build();
        mockEmployeeDto = modelMapper.map(mockEmployee, EmployeeDto.class);     // 7.3 Convert the Employee entity into EmployeeDto using ModelMapper for testing
    }

    // 6. Create a test method
    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_thenReturnEmployeeDto(){
        // Assign
        // 8.1 Prepare the test input and define behaviour of mocked repository
        Long id = mockEmployee.getId();
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
        verify(employeeRepository, times(1)).findById(id);                        // Verify that repository.findById(id) was called exactly once
    }

    @Test
    void createNewEmployee_whenValidEmployee_thenCreateNewEmployee(){
        // Assign
        // 9.1 Prepare the mocked behaviour for repository methods
        Mockito.when(employeeRepository.findByEmail(anyString())).thenReturn(List.of()); // Stub the repository method so that when findByEmail() is called with any email, it returns an empty list (meaning no employee with that email already exists)
        Mockito.when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee); // Stub the save() method so that whenever any Employee object is passed to save(), it returns mockEmployee instead of saving in a real database

        // Act
        // 9.2 Call the service method that we want to test
        EmployeeDto employeeDto = employeeService.createNewEmployee(mockEmployeeDto); // This executes the actual service logic which internally checks email using findByEmail() and then calls repository.save() to store the new employee

        // Assert
        // 9.3 Verify that the returned result is correct
        assertThat(employeeDto).isNotNull();                                        // Check that the returned EmployeeDto is not null
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());   // Verify that the email in returned DTO matches the email of the input DTO

        // We have saved new entity in repository but we don't know what object was actually passed to save() so we use ArgumentCaptor to capture the Employee object that was passed to save()
        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class); // Create an ArgumentCaptor to capture the Employee object passed to repository.save()
        verify(employeeRepository).save(employeeArgumentCaptor.capture()); // Verify that save() method was called and capture the Employee object that was passed to it

        Employee captureValue = employeeArgumentCaptor.getValue();              // Retrieve the captured Employee object that was passed to save()
        assertThat(captureValue.getEmail()).isEqualTo(mockEmployee.getEmail()); // Verify that the email of the captured Employee matches the expected email
    }
}