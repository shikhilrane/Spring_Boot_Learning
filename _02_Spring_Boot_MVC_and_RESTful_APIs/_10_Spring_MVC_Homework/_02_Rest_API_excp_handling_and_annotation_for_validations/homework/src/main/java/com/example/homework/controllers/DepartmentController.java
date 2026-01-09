package com.example.homework.controllers;

import com.example.homework.dto.DepartmentDto;
import com.example.homework.exceptions.ResourceNotFound;
import com.example.homework.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // 1. Get Single ID
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable Long departmentId){
        Optional<DepartmentDto> departmentDto = departmentService.getDepartmentById(departmentId);
        return departmentDto
                .map(departmentDto1 -> ResponseEntity.ok(departmentDto1))
                .orElseThrow(() -> new ResourceNotFound("Resource Not Found with id : " + departmentId));
    }

    // 2. Get All IDs
    @GetMapping("/department/findAll")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments(){
        List<DepartmentDto> allDepartment = departmentService.getAllDepartment();
        return ResponseEntity.ok(allDepartment);
    }

    // 3. Creating Department
    @PostMapping("/department/createDepartment")
    public ResponseEntity<DepartmentDto> createDepartment(@RequestBody @Valid DepartmentDto departmentDto){
        DepartmentDto newDepartment = departmentService.createNewDepartment(departmentDto);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    // 4. Update Specific Department
    @PutMapping("/department/{departmentId}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody @Valid DepartmentDto departmentDto, @PathVariable (name = "departmentId") Long id){
        DepartmentDto departmentDtoFromService = departmentService.updateDepartment(id, departmentDto);
        return ResponseEntity.ok(departmentDto);
    }

    // 5. Delete Department
    @DeleteMapping("/department/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartment(@PathVariable (name = "departmentId") Long id){
        boolean departmentById = departmentService.deleteDepartmentById(id);
        if (departmentById) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    // 6. Update Partial Info
    @PatchMapping("/department/{departmentId}")
    public ResponseEntity<DepartmentDto> partialUpdate(@RequestBody @Valid Map<String,Object> updates, @PathVariable (name = "departmentId") Long id){
        DepartmentDto departmentDto =  departmentService.partialUpdate(id, updates);
        if (departmentDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(departmentDto);
    }
}
