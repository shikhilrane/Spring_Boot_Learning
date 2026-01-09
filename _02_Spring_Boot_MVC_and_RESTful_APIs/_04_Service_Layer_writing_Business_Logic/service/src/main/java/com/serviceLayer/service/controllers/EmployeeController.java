package com.serviceLayer.service.controllers;

import com.serviceLayer.service.dto.EmployeeDto;
import com.serviceLayer.service.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/employees")    // This will become main path url for all employees mapping
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService)  {
        this.employeeService = employeeService;
    }


    @GetMapping(path = "/{employeeId}")
    public EmployeeDto getEmployeeById(@PathVariable (name = "employeeId") Long id){
        return employeeService.getEmployeeById(id); // Call the service layer to get the employee by ID and return the result as a DTO
    }

    @GetMapping(path = "/findAll")                          // this will be mapped to /employees
    public List<EmployeeDto> getAllEmployees(@RequestParam (required = false) Integer age,// as we know @RequestParam is required field so remove compulsion use false
                                                @RequestParam (required = false) String sortBy){
        return employeeService.getAllEmployees();
    }

    @PostMapping(path = "/createEmployee")
    public EmployeeDto createNewEmployee(@RequestBody EmployeeDto inputEmployee){
        return employeeService.createNewEmployee(inputEmployee);
    }

}

// Controller ---------> Service ---------> Repository
//                ^                  ^
//                |                  |
//               DTO---------------Entity
//                          ^
//                          |
//                          |
//                          |
//                     ModelMapper
