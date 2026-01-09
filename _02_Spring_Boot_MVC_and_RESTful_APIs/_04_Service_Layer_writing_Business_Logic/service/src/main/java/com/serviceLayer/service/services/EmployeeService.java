package com.serviceLayer.service.services;

import com.serviceLayer.service.dto.EmployeeDto;
import com.serviceLayer.service.entities.EmployeeEntity;
import com.serviceLayer.service.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public EmployeeDto getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElse(null);   // Fetch the employee entity from the database by ID; return null if not found
        return modelMapper.map(employeeEntity, EmployeeDto.class);                  // Convert the fetched EmployeeEntity to EmployeeDto and return it
    }

    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll(); // Retrieve all employee records from the database as a list of EmployeeEntity objects

        // Convert each EmployeeEntity to EmployeeDto using ModelMapper,
        // then collect the results into a list and return it
        return employeeEntities
                .stream()               // Convert the list to a stream for processing
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDto.class)) // Map each entity to a DTO
                .collect(Collectors.toList());  // Collect the mapped DTOs into a list
    }

    public EmployeeDto createNewEmployee(EmployeeDto inputEmployee) {
        EmployeeEntity toSaveEntity = modelMapper.map(inputEmployee, EmployeeEntity.class); // Convert the incoming EmployeeDto to an EmployeeEntity so it can be saved to the database
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(toSaveEntity); // Save the converted entity to the database and get the saved entity (with any generated fields like ID)
        return modelMapper.map(savedEmployeeEntity, EmployeeDto.class); // Convert the saved entity back to EmployeeDto to return it (e.g., for sending response)
    }
}

// @Service -> Use to mark as class as service class. In @Service it has @Component so it can become bean