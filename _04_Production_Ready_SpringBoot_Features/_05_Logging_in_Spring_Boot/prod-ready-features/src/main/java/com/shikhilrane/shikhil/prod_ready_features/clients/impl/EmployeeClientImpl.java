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

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;
    private static final Logger log = LoggerFactory.getLogger(EmployeeClientImpl.class);

    // Get all Employees
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
            ResponseEntity<APIResponse<List<EmployeeDto>>> employeeDtoList = restClient.get()   // create GET request (We don't use ResponseEntity<> in Service Layer but here we used, because it is not Producing HTTP Response, instead it is Consuming HTTP Response from foreign API and Same for APIResponse)

                    .uri("/employees/findAll")                              // endpoint to get all employees
                    .retrieve()                                                 // execute HTTP call
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Client error while fetching employees"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Server error while fetching employees"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});               // convert response body
            APIResponse<List<EmployeeDto>> body = employeeDtoList.getBody();
            if (body == null) {
                throw new RuntimeException("Empty response from Employee service");
            }
            log.trace("Successfully retrieved the Employees in getAllEmployees for trace : {}", employeeDtoList.getBody().getData());
            log.info("Successfully retrieved the Employees in getAllEmployees, it is use to give information that our application is successfully executed");
            return employeeDtoList.getBody().getData(); // return employee list from response
        } catch (Exception e) {
            log.error("Exception occurred in getAllEmployees : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    // Get Employee by ID
    @Override
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        log.trace("Trying to fetch Employee by ID");
        try {
            ResponseEntity<APIResponse<EmployeeDto>> response = restClient.get()                            // create GET request
                    .uri("/employees/{employeeId}", id)                                 // endpoint with path variable
                    .retrieve()                                                             // execute HTTP call
                    .onStatus(
                            status -> status.value() == 404,
                            (req, res) -> {
                                log.debug("404 response body: {}", res.getBody());
                                log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new ResourceNotFoundException("Employee not found with id : " + id); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Server error while fetching employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<APIResponse<EmployeeDto>>() {});   // map wrapper
            APIResponse<EmployeeDto> body = response.getBody();
            if (body == null) {
                throw new RuntimeException("Empty response from Employee service");
            }
            log.trace("Successfully retrieved the Employee in getEmployeeById for trace : {}", response.getBody().getData());
            log.info("Successfully retrieved the Employee in getEmployeeById, it is use to give information that our application is successfully executed");
            return Optional.ofNullable(response.getBody().getData());                                 // extract actual employee
        } catch (Exception e) {
            log.error("Exception occurred in getEmployeeById", e);
            throw new RuntimeException(e);
        }
    }

    // Create Employee
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        log.trace("Trying to create an Employee");
        try {
            ResponseEntity<APIResponse<EmployeeDto>> employeeDtoAPIResponse = restClient.post()     // create POST request
                    .uri("/employees/createEmployee")                           // endpoint to create employee
                    .body(employeeDto)                                              // send employee data
                    .retrieve()                                                     // execute HTTP call
                    .onStatus(
                            status -> status.is4xxClientError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Invalid employee data"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Server error while creating employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});                   // map response body
            log.trace("Successfully created an employee for trace : {}", employeeDtoAPIResponse.getBody().getData());
            log.info("Successfully created an employee, it is use to give information that our application is successfully executed");
            return employeeDtoAPIResponse.getBody().getData();                                // return created employee
        } catch (Exception e) {
            log.error("Exception occurred in getEmployeeById : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    // Update Employee
    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.trace("Trying to update an Employee");
        try {
            ResponseEntity<APIResponse<EmployeeDto>> response = restClient.put()    // create PUT request
                    .uri("/employees/{id}", id)                 // endpoint with employee id
                    .body(employeeDto)                              // send updated employee data
                    .retrieve()                                     // execute HTTP call
                    .onStatus(
                            status -> status.value() == 404,
                            (req, res) -> {
                                log.debug("404 response body: {}", res.getBody());
                                log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Employee not found for update"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Server error while updating employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});   // map response
            log.trace("Successfully updated an employee with id : {} for trace : {}", id, response.getBody().getData());
            log.info("Successfully updated an employee, it is use to give information that our application is successfully executed");
            return response.getBody().getData();                              // return updated employee
        } catch (Exception e) {
            log.error("Exception occurred in getEmployeeById : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    // Patch Employee
    @Override
    public EmployeeDto updatePartialEmployee(Long id, Map<String, Object> updates) {
        log.trace("Trying to patch an Employee");
        try {
            ResponseEntity<APIResponse<EmployeeDto>> response = restClient.patch()      // create PATCH request
                    .uri("/employees/{id}", id)                     // endpoint with employee id
                    .body(updates)                                      // send only fields to update
                    .retrieve()                                         // execute HTTP call
                    .onStatus(
                            status -> status.value() == 404,
                            (req, res) -> {
                                log.debug("404 response body: {}", res.getBody());
                                log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Employee not found for pstch"); }
                    )
                    .onStatus(
                            status -> status.is5xxServerError(),
                            (req, res) -> {
                                log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                                log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                                throw new RuntimeException("Server error while patching employee"); }
                    )
                    .toEntity(new ParameterizedTypeReference<>() {});       // map response
            log.trace("Successfully patched an employee with id : {} for trace : {}", id, response.getBody().getData());
            log.info("Successfully patched an employee, it is use to give information that our application is successfully executed");
            return response.getBody().getData();                                  // return updated employee
        }catch (Exception e) {
            log.error("Exception occurred in getEmployeeById : {}", String.valueOf(e));
            throw new RuntimeException(e);
        }
    }

    // Delete Employee
    @Override
    public void deleteEmployee(Long id) {
        restClient.delete()                     // create DELETE request
                .uri("/employees/{id}", id) // endpoint with employee id
                .retrieve()                     // execute HTTP call
                .onStatus(
                        status -> status.value() == 404,
                        (req, res) -> {
                            log.debug("404 response body: {}", res.getBody());
                            log.error("{} error from client. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                            throw new RuntimeException("Employee not found for delete"); }
                )
                .onStatus(
                        status -> status.is5xxServerError(),
                        (req, res) -> {
                            log.debug("{} response body: {}", res.getStatusCode(), res.getBody());
                            log.error("{} error from server. Status: {}, Reason: {}", res.getStatusCode(), res.getStatusCode(), res.getStatusText());
                            throw new RuntimeException("Server error while deleting employee"); }
                )
                .toBodilessEntity();            // no response body needed
    }
}
