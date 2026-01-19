package com.presentationLayer.PresentationLayer.controllers;

import com.presentationLayer.PresentationLayer.dto.EmployeeDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/employees")    // This will become main path url for all employees mapping
public class EmployeeController {

//    @GetMapping(path = "/employees/getSecretMsg")     // Before using @RequestMapping
    @GetMapping(path = "/getSecretMsg")
    public String getMySuperSecretMessage(){
        return "Secret message @54e5#43w";
    }

//    @GetMapping(path = "/employees/{employeeID}")     // Before using @RequestMapping
    @GetMapping(path = "/{employeeID}")
    public EmployeeDto getEmployeeById(@PathVariable (name = "employeeID") long id){
        return new EmployeeDto(id, "Shikhil", "skr@gmail.com", 28, LocalDate.of(2025, 10, 12), true);
        // http://localhost:8080/employees/1
    }

//    @GetMapping(path= "/employees")                   // Before using @RequestMapping
    @GetMapping                                         // this will be mapped to /employees
    public String getAllEmployees(@RequestParam (required = false) Integer age,    // as we know @RequestParam is required field, so remove compulsion use false
                                  @RequestParam (required = false) String sortBy){
        return "Hi age " + age + " " + sortBy;
        // http://localhost:8080/employees?age=16&sortBy=Shikhil
    }

    @PostMapping                            // POST will be only access through frontend or Postman
    public String createEmployee(){
        return "Hello from Post";
    }

    @PostMapping(path = "/createEmployee")
    public EmployeeDto createNewEmployee(@RequestBody EmployeeDto inputEmployee){
        inputEmployee.setId(10L);
        return inputEmployee;
    }
}

// @RestController ->  We have used @RestController instead of @Controller, so that it will return directly return JSON/XML directly to response body. Otherwise we have to use @ResponseBody to every controller to convert it to JSON/xml. So, @RestController = @Controller + @ResponseBody

// @RequestMapping -> Maps HTTP requests to handler methods (GET,PUT,DELETE,PATCH,POST) in controllers based on URL, method, headers, etc.

// @PathVariable -> Binds a value from the URL path to a method parameter in a controller.
// Example: /users/5 → @PathVariable Long id

// @RequestParam -> Binds a query parameter from the URL to a method parameter in a controller.
// Example: /search?keyword=book → @RequestParam String keyword

// @RequestBody -> Use to send complex data that we can't send through @PathVariable and @RequestParam. It is used to bind HTTP request bosy to a Java Object. When a client sends data in a body of request (JSON or XML), @RequestBody maps this to Java object.
