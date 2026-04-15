package com.shikhilrane.testing.TestingApplication.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest                                             // 3. 5. Give this annotation for configuring testing in the whole class
@DataJpaTest                  // 5. this will only scan within repository level and also use test db according to scope from pom.xml
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)     // 4. 5. Replace Real DB with Test DB
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;          // 2. Autowire the repository for testing

    @Test
    void testFindByEmail_whenEmailIsValid_thenReturnEmployee() {        // 1. Give proper name to test method

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){

    }
}