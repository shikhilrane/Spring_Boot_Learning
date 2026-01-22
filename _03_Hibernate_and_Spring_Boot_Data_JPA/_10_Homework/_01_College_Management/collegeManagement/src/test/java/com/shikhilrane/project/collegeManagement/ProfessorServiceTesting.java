package com.shikhilrane.project.collegeManagement;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class ProfessorServiceTesting {

    @Autowired
    private ProfessorService professorService;

    // Post
    @Test
    public void testCreatedProfessor(){
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setTitle("Dr. Chandan");

        var testingCreatedService = professorService.creatingProfessor(professorDto);

        System.out.println(testingCreatedService.getTitle());
    }

    // Get by Id
    @Test
    public void testGetProfessorById(){
        Optional<ProfessorDto> professorById = professorService.getProfessorById(3L); // Use @ToString in DTO to print Optional
        System.out.println(professorById);
    }

    // Get All
    @Test
    public void testGetAllProfessors(){
        List<ProfessorDto> allProfessor = professorService.getAllProfessor();
        System.out.println(allProfessor);
    }

    // Put
    @Test
    public void testUpdateProfessor(){
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setTitle("Dr. Hemant");

        ProfessorDto updatedProfessorInfo = professorService.updateProfessorInfo(3L, professorDto);

        System.out.println(updatedProfessorInfo);
    }

    // Delete
    @Test
    public void testDeleteProfessor(){
        boolean deleted = professorService.deleteProfessor(3L);
        System.out.println(deleted);
    }

    // Ptch
    @Test
    public void testPatchProfessor(){
        Map<String, Object> update = new HashMap<>();
        update.put("title", "Dr. Zeeshan");

        var patched = professorService.patchPrpfessor(4L, update);
        System.out.println(patched);
    }
}
