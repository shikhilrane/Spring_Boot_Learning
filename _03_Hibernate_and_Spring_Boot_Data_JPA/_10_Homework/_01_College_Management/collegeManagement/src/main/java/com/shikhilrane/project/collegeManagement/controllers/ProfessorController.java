package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    // Post Mapping
    @PostMapping("/createProfessor")
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professorDto){
        ProfessorDto createdProfessorDto = professorService.creatingProfessor(professorDto);
        return new ResponseEntity<>(createdProfessorDto, HttpStatus.CREATED);
    }

    // Get Mapping (by Id)
    @GetMapping("/{getById}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable(name = "getById") Long id){
        Optional<ProfessorDto> gotProfessorById = professorService.getProfessorById(id);
        return gotProfessorById
                .map(gotProfessorById1 -> ResponseEntity.ok(gotProfessorById1))
                .orElse(ResponseEntity.notFound().build());
    }

    // Get Mapping (get all)
    @GetMapping("/getAllProfessors")
    public ResponseEntity<List<ProfessorDto>> getAllProfessor(){
        List<ProfessorDto> allProfessor = professorService.getAllProfessor();
        return ResponseEntity.ok(allProfessor);
    }


}
