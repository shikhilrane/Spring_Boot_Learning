package com.putPatchDelete.putPatchDeleteOperations.services;

import com.putPatchDelete.putPatchDeleteOperations.dto.EmployeeDto;
import com.putPatchDelete.putPatchDeleteOperations.entities.EmployeeEntity;
import com.putPatchDelete.putPatchDeleteOperations.repositories.EmployeeRepository;
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

    // 4. Update Employee (PUT)
    public EmployeeDto updateEmployeeByID(Long id, EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class); // Convert DTO to Entity
        employeeEntity.setId(id);                                                   // Set the correct ID
        EmployeeEntity updatedEntity = employeeRepository.save(employeeEntity);     // Save the updated entity
        return modelMapper.map(updatedEntity, EmployeeDto.class);                   // Convert the updated entity back to DTO and return
    }

    public boolean isExistByEmployeeID(Long id){    // Checks if an employee with the given ID exists in the repository
        return employeeRepository.existsById(id);   // Returns true if an employee with the given ID exists, false otherwise
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
        return modelMapper.map(employeeRepository.save(employeeEntity), EmployeeDto.class); // Save the updated entity to the repository and map it to EmployeeDto before returning
    }
}

// @Service -> Use to mark as class as service class. In @Service it has @Component so it can become bean

/*

    Receiving - private final Repository repository;
    Return - DTO

    ModelMapper - ModelMapper is used to automatically map one Java object to another, typically between DTOs and entities or vice versa.

    Method for GET ->
        1. Return Type - Optional<DTO>
        2. Parameter - As we have passed @PathVariable Long id, so we have to pass id here as well
        3. Receiving from DB - using repository and using Repository method findById(pass id) and save it Optional<Entity> entity
        4. Return - apply .map method on entity from 3. (pass new entity in it and using modelMapper.map(new entity with DTO.class))

    Method for GET (for all) ->
        1. Return Type - List<DTO>
        2. Parameter - Nothing
        3. Receiving from DB - using repository and using Repository method findAll() and save it List<Entity> entity
        4. Return - apply .map method on entity from 3. And apply .stream() .map(and pass new entity and return dto using modelMapper) .collect(collect in toList() of Collectors) methods

    Method for POST ->
        1. Return Type - DTO
        2. Parameter - Pass dto
        3. Push from DB - Using modelMapper, map the dto with Entity.class and save it to Entity
                          And using repository and using Repository method save(entity from modelMapper) and save it in Entity
        4. Return - map(entity from 3. with DTO.class) using modelMapper

    Method for PUT ->
        1. Return Type - DTO
        2. Parameter - id, Pass dto
        3. Receiving from DB - Using modelMapper, map the dto with Entity.class and save it to entity
                               use .set on entity and pass id in it
                               And using repository and using Repository method save(entity to it) and save it in Entity
        4. Return - map(entity from 3. with DTO.class) using modelMapper

    Method for DELETE ->
        1. Return Type - boolean
        2. Parameter - id
        3. Receiving from DB - using repository and using Repository method existsById(pass id) and save it boolean exist, use to check if this id present in DB or not
        4. Return - return false, if(!exist)
                    on repository use deleteById(pass id) in it
                    return true

    Method for PATCH ->
        1. Return type - DTO
        2. Parameter - id, Map<String, Object> updates
        3. Receiving from DB - using repository and using Repository method existsById(pass id) and save it boolean exist, use to check if this id present in DB or not
        4. Return - return null, if(!exist)
                    apply repository's method findById(pass id) and get() and store it in new entity
                    If the employee DOES exist:
                    Fetch the existing EmployeeEntity using findById(id).get()
                    Loop through all entries in the 'updates' map
                    For each entry:
                      Find the corresponding field in EmployeeEntity using reflection
                      Make the field accessible
                      Set the new value on the entity
                    Save the modified entity back to the database using save()
                    Convert the saved entity into EmployeeDTO using modelMapper
                    return map(entity from lastly created. with DTO.class) using modelMapper
*/