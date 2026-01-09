package com.putPatchDelete.putPatchDeleteOperations.controllers;

import com.putPatchDelete.putPatchDeleteOperations.dto.EmployeeDto;
import com.putPatchDelete.putPatchDeleteOperations.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")    // This will become main path url for all employees mapping
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
                .map(employeeDto1 -> ResponseEntity.ok(employeeDto1))   // if employee found
                .orElse(ResponseEntity.notFound().build());                         // if not found
    }

    // 2. Find all Employees (GET)
    @GetMapping(path = "/findAll")                          // this will be mapped to /employees
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam (required = false) Integer age,// as we know @RequestParam is required field so remove compulsion use false
                                                @RequestParam (required = false) String sortBy){
        return ResponseEntity.ok(employeeService.getAllEmployees()); // Call the service to get all employees (note: you're currently not using age or sortBy here)
    }

    // 3. Create new Employee (POST)
    @PostMapping(path = "/createEmployee")
    public ResponseEntity<EmployeeDto> createNewEmployee(@RequestBody EmployeeDto inputEmployee){
        EmployeeDto savedEmployee = employeeService.createNewEmployee(inputEmployee); // Calls the service layer to save the new employee and returns the saved result
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED); // Returns the saved employee with HTTP status 201 (Created)
    }

    // 4. Update Employee (PUT)
    @PutMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeByID(@RequestBody EmployeeDto employeeDto, @PathVariable (name = "employeeId") Long id){
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

/*
    @RestController = @Controller + @ResponseBody

    Receiving from Service by private final Service service;
    Return DTO

    ResponseEntity is used to show status codes for each and every apis.

    GET -> getting single data from DB
        1. Annotation - @GetMapping("/url/{id}")  <- It will return data for id 1
        2. Return type - ResponseEntity<EmployeeDTO>
        3. Parameters - To get the id @PathVariable we can rename it by using (name=)
        4. Receiving - from service and passing id from parameter to get it and save it in <Optional> ( <Optional> is simply used to represent a value that may or may not be present)
        5. Return - Return DTO, and map (return new dto and apply .ok method on ResponseEntity and pass newly created object in it) OR orElse(ResponseEntity.notFound().build())

    GET (getting all data) ->
        1. Annotation - @GetMapping("url")  <- It will return all data
        2. Return Type - ResponseEntity<List<EmployeeDTO>>
        3. Parameters - Nothing
        4. Receiving - from service call the method and save it in DTO and in List
        5. Return - Return DTO, and apply .ok method on ResponseEntity and pass List of DTO from 3.

    POST ->
        1. Annotation - @PostMapping("url/postUser")  <- It will save data
        2. Return Type - ResponseEntity<EmployeeDTO>
        3. Parameters - @RequestBody Dto dto, (@RequestBody it converts json to java object using jackson (and @ResponseBody is reverse of it))
        4. Receiving - from service call the method and pass dto in it and save it in DTO
        5. Return - Return new ResponseEntity<>, and pass saved dto created from 4 and HttpStatus.CREATED in it

    PUT ->
        1. Annotation - @PutMapping("/url/{id}")  <- It will return data for id 1 to update user
        2. Return Type - ResponseEntity<EmployeeDTO>
        3. Parameters - @RequestBody Dto dto, and @PathVariable Long id in it
        4. Receiving - from service call the method, and pass id and dto in it and save it in DTO
        5. Return - Return ResponseEntity with .ok and pass DTO created from 4

    DELETE ->
        1. Annotation - @DeleteMapping("/url/{id}")  <- It will get data for id 1 to delete user
        2. Return Type - ResponseEntity<Boolean>
        3. Parameters - @PathVariable Long id in it
        4. Receiving - from service call the method, and pass id and save it in Boolean
        5. Return - if deleted from DB using 4. return ResponseEntity.ok(true) OR return ResponseEntity.notFound().build();

    PATCH ->
        1. Annotation - @PatchMapping("/url/{id}")  <- It will return data for id 1 to partially update user
        2. Return Type - ResponseEntity<EmployeeDTO>
        3. Parameters - @RequestBody Map<String,object> updates (it will get what to edit), and @PathVariable Long id in it
        4. Receiving - from service call the method, and pass id and updates in it and save it in DTO
        5. Return - if user not found then return ResponseEntity.notFound().build() OR return ResponseEntity.ok(DTO from 4.)
*/