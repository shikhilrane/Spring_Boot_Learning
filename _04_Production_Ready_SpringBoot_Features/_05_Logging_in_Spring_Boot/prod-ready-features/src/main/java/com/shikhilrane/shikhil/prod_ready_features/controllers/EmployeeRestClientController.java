package com.shikhilrane.shikhil.prod_ready_features.controllers;

import com.shikhilrane.shikhil.prod_ready_features.clients.EmployeeClient;
import com.shikhilrane.shikhil.prod_ready_features.dto.EmployeeDto;
import com.shikhilrane.shikhil.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/client/employees")
@RequiredArgsConstructor
public class EmployeeRestClientController {

    private final EmployeeClient employeeClient;

    // GET ALL
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeClient.getAllEmployees());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDto> employee = employeeClient.getEmployeeById(id);
        return employee
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found  with id : " + id));
    }

    // CREATE
    @PostMapping("/getAllEmployees")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto saved = employeeClient.createEmployee(employeeDto);
        return ResponseEntity.ok(saved);
    }

    // UPDATE (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeDto employeeDto
    ) {
        return ResponseEntity.ok(employeeClient.updateEmployee(id, employeeDto));
    }

    // UPDATE PARTIAL (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updatePartialEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ) {
        return ResponseEntity.ok(employeeClient.updatePartialEmployee(id, updates));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeClient.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

}
