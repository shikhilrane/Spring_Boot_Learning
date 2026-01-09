package com.exceptionHandling.ExceptionHandling.controllers;

import com.exceptionHandling.ExceptionHandling.dto.EmployeeDto;
import com.exceptionHandling.ExceptionHandling.exceptions.ResourceNotFoundExeption;
import com.exceptionHandling.ExceptionHandling.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")    // This will become main path url for all employees mapping
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 1. Find Employee by ID (GET)
    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable (name = "employeeId") Long id){
        Optional<EmployeeDto> employeeDto = employeeService.getEmployeeById(id); // Ask the service to get the employee by ID (returns Optional — could be empty if not found) (Optional is a container object in Java that may or may not hold a non-null value — it's used to avoid null checks and prevent NullPointerException.)
        return employeeDto      // If present, return 200 OK with the employee data; if not, return 404 Not Found
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1))       // if employee found
                .orElseThrow(() -> new ResourceNotFoundExeption("Employee not found with ID = " + id));   // if not found, Throw related @ExceptionHandler
    }

//    @ExceptionHandler(NoSuchElementException.class) (commented because we wrote logic for global exception for this code in GlobalExceptionHandler class)
//    public ResponseEntity<String> handleGetEmployeeById(NoSuchElementException exception){
//        return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
//    }
    // This way we can throw exception but not an ideal way because it is only limited up to this method only

    // 2. Find all Employees (GET)
    @GetMapping(path = "/findAll")                          // this will be mapped to /employees
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam (required = false) Integer age,// as we know @RequestParam is required field so remove compulsion use false
                                                @RequestParam (required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees()); // Call the service to get all employees (note: you're currently not using age or sortBy here)
    }

    // 3. Create new Employee (POST)
    @PostMapping(path = "/createEmployee")
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody @Valid EmployeeDto inputEmployee){
        EmployeeDto savedEmployee = employeeService.createNewEmployee(inputEmployee); // Calls the service layer to save the new employee and returns the saved result
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED); // Returns the saved employee with HTTP status 201 (Created)
    }

    // 4. Update Employee (PUT)
    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeByID(@RequestBody @Valid EmployeeDto employeeDto, @PathVariable (name = "employeeId") Long id){
        return ResponseEntity.ok(employeeService.updateEmployeeByID(id, employeeDto)); // Calls the service method to update the employee and returns the updated result with 200 OK
    }

    // 5. Delete the Employee (DELETE)
    @DeleteMapping(path = "/{employeeId}")
    public ResponseEntity<Boolean> deleteEmpl(@PathVariable (name = "employeeId") Long id){
        boolean gotDeleted = employeeService.deleteEmpl(id);    // Calls the service to attempt deleting the employee
        if (gotDeleted) return ResponseEntity.ok(true);   // If deleted successfully, return 200 OK with true
        return ResponseEntity.notFound().build();               // If not found, return 404 Not Found
    }

    // 6. Update Partial information (PATCH)
    @PatchMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updatePartialEmpl(@RequestBody Map<String, Object> updates, @PathVariable (name = "employeeId") Long id){
        EmployeeDto employeeDto = employeeService.updatePartialEmpl(id, updates); // Calls the service to apply partial updates and get the updated employee
        if (employeeDto == null) return ResponseEntity.notFound().build();        // If employee not found (null), return 404 Not Found
        return ResponseEntity.ok(employeeDto);                                    // If update successful, return updated employee with 200 OK
    }

}

// Controller ---------> Service ---------> Repository
//                ^                  ^
//                |                  |
//               DTO               Entity