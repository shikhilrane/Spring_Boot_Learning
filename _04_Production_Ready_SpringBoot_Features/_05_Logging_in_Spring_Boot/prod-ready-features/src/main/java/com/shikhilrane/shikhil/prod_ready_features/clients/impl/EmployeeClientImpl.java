package com.shikhilrane.shikhil.prod_ready_features.clients.impl;

import com.shikhilrane.shikhil.prod_ready_features.advice.APIResponse;
import com.shikhilrane.shikhil.prod_ready_features.clients.EmployeeClient;
import com.shikhilrane.shikhil.prod_ready_features.dto.EmployeeDto;
import com.shikhilrane.shikhil.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDto> getAllEmployees() {
//        log.error("error log");
//        log.warn("warn log");
//        log.info("info log");
//        log.debug("debug log");
//        log.trace("trace log");
        // But these logs are not useful to write here

        log.trace("Trying to fetch all Employees");
        try {
            ResponseEntity<APIResponse<List<EmployeeDto>>> employeeDtoList = restClient.get()   // create GET request
                    .uri("/employees/findAll")                              // endpoint to get all employees
                    .retrieve()                                                 // execute HTTP call
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (req, res) -> {
                                log.debug("4xx error occurred. So,could not fetch the Employee List");
                                log.error("error{}", Arrays.toString(res.getBody().readAllBytes()));
                                throw new RuntimeException("Client error while fetching employees"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("5xx error occurred. So,could not fetch the Employee List");
                                log.error("error{}", Arrays.toString(res.getBody().readAllBytes()));
                                throw new RuntimeException("Server error while fetching employees"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});               // convert response body
            log.trace("Successfully retrieved the Employees in getAllEmployees for trace : {}", employeeDtoList.getBody().getData());
            log.info("Successfully retrieved the Employees in getAllEmployees, it is use to give information that our application is successfully executed");
            return employeeDtoList.getBody().getData(); // return employee list from response
        } catch (Exception e) {
            log.error("Exception occurred in getAllEmployees : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        try {
            ResponseEntity<APIResponse<EmployeeDto>> response = restClient.get()                            // create GET request
                    .uri("/employees/{employeeId}", id)                                 // endpoint with path variable
                    .retrieve()                                                             // execute HTTP call
                    .onStatus(
                            status -> status.value() == 404,
                            (req, res) -> {
                                throw new ResourceNotFoundException("Employee not found with id : " + id); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                throw new RuntimeException("Server error while fetching employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<APIResponse<EmployeeDto>>() {});   // map wrapper

            return Optional.ofNullable(response.getBody().getData());                                 // extract actual employee
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        try {
            ResponseEntity<APIResponse<EmployeeDto>> employeeDtoAPIResponse = restClient.post()     // create POST request
                    .uri("/employees/createEmployee")                           // endpoint to create employee
                    .body(employeeDto)                                              // send employee data
                    .retrieve()                                                     // execute HTTP call
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (req, res) -> { throw new RuntimeException("Invalid employee data"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> { throw new RuntimeException("Server error while creating employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});                   // map response body
            return employeeDtoAPIResponse.getBody().getData();                                // return created employee
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        try {
            ResponseEntity<APIResponse<EmployeeDto>> response = restClient.put()    // create PUT request
                    .uri("/employees/{id}", id)                 // endpoint with employee id
                    .body(employeeDto)                              // send updated employee data
                    .retrieve()                                     // execute HTTP call
                    .onStatus(
                            status -> status.value() == 404,
                            (req, res) -> { throw new RuntimeException("Employee not found for update"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> { throw new RuntimeException("Server error while updating employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});   // map response
            return response.getBody().getData();                              // return updated employee
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDto updatePartialEmployee(Long id, Map<String, Object> updates) {
        ResponseEntity<APIResponse<EmployeeDto>> response = restClient.patch()      // create PATCH request
                .uri("/employees/{id}", id)                     // endpoint with employee id
                .body(updates)                                      // send only fields to update
                .retrieve()                                         // execute HTTP call
                .onStatus(
                        status -> status.value() == 404,
                        (req, res) -> { throw new RuntimeException("Employee not found for patch"); }
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        (req, res) -> { throw new RuntimeException("Server error while patching employee"); }
                )
                .toEntity(new ParameterizedTypeReference<>() {});       // map response
        return response.getBody().getData();                                  // return updated employee
    }

    @Override
    public void deleteEmployee(Long id) {
        restClient.delete()                     // create DELETE request
                .uri("/employees/{id}", id) // endpoint with employee id
                .retrieve()                     // execute HTTP call
                .onStatus(
                        status -> status.value() == 404,
                        (req, res) -> { throw new RuntimeException("Employee not found for delete"); }
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        (req, res) -> { throw new RuntimeException("Server error while deleting employee"); }
                )
                .toBodilessEntity();            // no response body needed
    }
}
