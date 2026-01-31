package com.shikhilrane.shikhil.prod_ready_features;

import com.shikhilrane.shikhil.prod_ready_features.clients.impl.EmployeeClientImpl;
import com.shikhilrane.shikhil.prod_ready_features.dto.EmployeeDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdReadyFeaturesApplicationTests {

    @Autowired
    private EmployeeClientImpl employeeClient;

	@Test
	void contextLoads() {
	}

    @Test
    @Order(3)
    public void getEmployees(){
        List<EmployeeDto> allEmployees = employeeClient.getAllEmployees();
        System.out.println(allEmployees);
    }

    @Test
    @Order(2)
    public void getEmployeeById(){
        Optional<EmployeeDto> employeeById = employeeClient.getEmployeeById(1L);
        System.out.println(employeeById);
    }

    @Test
    @Order(1)
    public void postEmployee(){
        EmployeeDto employeeDto = new EmployeeDto(null,"Sumit","sumit@gmail.com",35,"ADMIN",34565,23.45, LocalDate.of(2020,11,11),true);
        EmployeeDto employee = employeeClient.createEmployee(employeeDto);
        System.out.println(employeeDto);
    }
}
