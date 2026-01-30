package com.shikhilrane.shikhil.prod_ready_features.clients.impl;

import com.shikhilrane.shikhil.prod_ready_features.advice.APIResponse;
import com.shikhilrane.shikhil.prod_ready_features.clients.EmployeeClient;
import com.shikhilrane.shikhil.prod_ready_features.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        try {
            APIResponse<List<EmployeeDto>> employeeDtoList = restClient.get()   // create GET request
                    .uri("/employees/findAll")                              // endpoint to get all employees
                    .retrieve()                                                 // execute HTTP call
                    .body(new ParameterizedTypeReference<>() {});               // convert response body
            return employeeDtoList.getData(); // return employee list from response
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EmployeeDto> getEmployeeById(Long id) {
         try {
             Optional<EmployeeDto> gotEmployeeById = restClient.get()   // create GET request
                     .uri("/employees/{employeeId}", id)            // endpoint with path variable
                     .retrieve()                                        // execute HTTP call
                     .body(new ParameterizedTypeReference<>() {});      // map response to Optional
             return gotEmployeeById;                                    // return employee if present
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        try {
            APIResponse<EmployeeDto> employeeDtoAPIResponse = restClient.post()     // create POST request
                    .uri("/employees/createEmployee")                           // endpoint to create employee
                    .body(employeeDto)                                              // send employee data
                    .retrieve()                                                     // execute HTTP call
                    .body(new ParameterizedTypeReference<>() {});                   // map response body
            return employeeDtoAPIResponse.getData();                                // return created employee
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        try {
            APIResponse<EmployeeDto> response = restClient.put()    // create PUT request
                    .uri("/employees/{id}", id)                 // endpoint with employee id
                    .body(employeeDto)                              // send updated employee data
                    .retrieve()                                     // execute HTTP call
                    .body(new ParameterizedTypeReference<>() {});   // map response
            return response.getData();                              // return updated employee
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto updatePartialEmployee(Long id, Map<String, Object> updates) {
        APIResponse<EmployeeDto> response = restClient.patch()      // create PATCH request
                .uri("/employees/{id}", id)                     // endpoint with employee id
                .body(updates)                                      // send only fields to update
                .retrieve()                                         // execute HTTP call
                .body(new ParameterizedTypeReference<>() {});       // map response
        return response.getData();                                  // return updated employee
    }

    @Override
    public void deleteEmployee(Long id) {
        restClient.delete()                     // create DELETE request
                .uri("/employees/{id}", id) // endpoint with employee id
                .retrieve()                     // execute HTTP call
                .toBodilessEntity();            // no response body needed
    }
}
