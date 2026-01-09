package com.inputValidation.inputValidationCustomAnnotation.services;

import com.inputValidation.inputValidationCustomAnnotation.dto.EmployeeDto;
import com.inputValidation.inputValidationCustomAnnotation.entities.EmployeeEntity;
import com.inputValidation.inputValidationCustomAnnotation.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    // 1. Find Employee by ID (GET)
    public Optional<EmployeeDto> getEmployeeById(Long id) {
        Optional <EmployeeEntity> employeeEntity = employeeRepository.findById(id); // Try to find the employee entity from the repository by ID (might be empty if not found)
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDto.class)); // If found, map the EmployeeEntity to EmployeeDto using modelMapper and return it inside Optional
    }

    // 2. Find all Employees (GET)
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(); // Retrieve all employee records from the database as a list of EmployeeEntity objects

        // Convert each EmployeeEntity to EmployeeDto using ModelMapper,
        // then collect the results into a list and return it
        return employeeEntities
                .stream()               // Convert the list to a stream for processing
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class)) // Map each entity to a DTO
                .collect(Collectors.toList());  // Collect the mapped DTOs into a list
    }

    // 3. Create new Employee (POST)
    public EmployeeDto createNewEmployee(EmployeeDto inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class); // Convert the incoming EmployeeDto to an EmployeeEntity so it can be saved to the database
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity); // Save the converted entity to the database and get the saved entity (with any generated fields like ID)
        return modelMapper.map(savedEmployeeEntity, EmployeeDto.class); // Convert the saved entity back to EmployeeDto to return it (e.g., for sending response)
    }

    public boolean isExistByEmployeeID(Long id){    // Checks if an employee with the given ID exists in the repository
        return employeeRepository.existsById(id);   // Returns true if an employee with the given ID exists, false otherwise
    }

    // 4. Update Employee (PUT)
    public EmployeeDto updateEmployeeByID(Long id, EmployeeDto employeeDto) {
        boolean exist = isExistByEmployeeID(id);                        // Check if employee exists before attempting delete
        if (!exist){
            return null;                                                // If employee doesn't exist, return null (nothing to update)
        }
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class); // Convert DTO to Entity
        employeeEntity.setId(id);                                                   // Set the correct ID
        EmployeeEntity updatedEntity = employeeRepository.save(employeeEntity);     // Save the updated entity
        return modelMapper.map(updatedEntity, EmployeeDto.class);                   // Convert the updated entity back to DTO and return
    }

    // 5. Delete the Employee (DELETE)
    public boolean deleteEmpl(Long id) {
        boolean exist = isExistByEmployeeID(id);    // Check if employee exists before attempting delete
        if (!exist){
            return false;                           // If employee does not exist, return false (deletion not possible)
        }else {
            employeeRepository.deleteById(id);      // If employee exists, delete employee by ID from repository
            return true;                            // Return true indicating successful deletion
        }
    }

    // 6. Update Partial information (PATCH)
    public EmployeeDto updatePartialEmpl(Long id, Map<String, Object> updates) {
        boolean exist = isExistByEmployeeID(id);    // Check if an employee with the given ID exists
        if (!exist){
            return null;                            // If employee doesn't exist, return null (nothing to update)
        }
        EmployeeEntity employeeEntity = employeeRepository.findById(id).get(); // Retrieve the existing EmployeeEntity from the repository by ID
        updates.forEach((key,value) -> {    // Iterate over each field update: key = field name, value = new value for that field
           Field fieldToUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class, key); // Get the Field object for the specified field name in EmployeeEntity class (throws if not found)
           fieldToUpdated.setAccessible(true);      // Make the field accessible for modification (even if it's private)
           ReflectionUtils.setField(fieldToUpdated, employeeEntity, value); // Set the new value for the field in the retrieved employeeEntity instance
        });
        EmployeeEntity updateSaved = employeeRepository.save(employeeEntity);
        return modelMapper.map(updateSaved, EmployeeDto.class); // Save the updated entity to the repository and map it to EmployeeDto before returning
    }
}

// @Service -> Use to mark as class as service class. In @Service it has @Component so it can become bean