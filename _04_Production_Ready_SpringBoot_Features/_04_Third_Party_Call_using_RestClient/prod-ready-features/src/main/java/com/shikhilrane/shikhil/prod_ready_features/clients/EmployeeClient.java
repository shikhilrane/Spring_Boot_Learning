package com.shikhilrane.shikhil.prod_ready_features.clients;

import com.shikhilrane.shikhil.prod_ready_features.dto.EmployeeDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeeClient {

    List<EmployeeDto> getAllEmployees();

    Optional<EmployeeDto> getEmployeeById(Long id);

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    EmployeeDto updatePartialEmployee(Long id, Map<String, Object> updates);

    void deleteEmployee(Long id);

}
