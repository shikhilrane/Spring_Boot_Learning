package com.shikhilrane.project.collegeManagement.controllers;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    // Put Mapping (Update)
    @PutMapping("/{updateProfessor}")
    public ResponseEntity<ProfessorDto> updateProfessor(@RequestBody ProfessorDto professorDto, @PathVariable(name = "updateProfessor") Long id){
        ProfessorDto updatedProfessorInfo = professorService.updateProfessorInfo(id, professorDto);
        return ResponseEntity.ok(updatedProfessorInfo);
    }

    // Delete mapping (Delete)
    @DeleteMapping("/{deleteProfessorById}")
    public ResponseEntity<Boolean> deleteprofessor(@PathVariable(name = "deleteProfessorById") Long id){
        boolean gotDeleted = professorService.deleteProfessor(id);
        if (gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    // Patch Mapping (Patch)
    @PatchMapping("/{patchProfessor}")
    public ResponseEntity<ProfessorDto> patchProfessor(@RequestBody Map<String, Object> updates, @PathVariable(name = "patchProfessor") Long id){
        ProfessorDto patchProfessorDto = professorService.patchPrpfessor(id, updates);
        if (patchProfessorDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(patchProfessorDto);
    }
}
