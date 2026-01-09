package com.persistenceLayer.JPA.controllers;

import com.persistenceLayer.JPA.dto.EmployeeDto;
import com.persistenceLayer.JPA.entities.EmployeeEntity;
import com.persistenceLayer.JPA.repositories.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")    // This will become main path url for all employees mapping
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/{employeeId}")
    public EmployeeEntity getEmployeeById(@PathVariable (name = "employeeId") Long id){
        return employeeRepository.findById(id).orElse(null);
    }

    @GetMapping(path = "/findAll")                          // this will be mapped to /employees
    public List<EmployeeEntity> getAllEmployees(@RequestParam (required = false) Integer age,// as we know @RequestParam is required field so remove compulsion use false
                                                @RequestParam (required = false) String sortBy){
        return employeeRepository.findAll();
    }

    @PostMapping(path = "/createEmployee")
    public EmployeeEntity createNewEmployee(@RequestBody EmployeeEntity inputEmployee){
        return employeeRepository.save(inputEmployee);
    }
}
