package com.shikhilrane.project.collegeManagement;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProfessorServiceTesting {

    @Autowired
    private ProfessorService professorService;

    @Test
    public void testCreatedProfessor(){
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setTitle("Dr. Chandan");

        var testingCreatedService = professorService.creatingProfessor(professorDto);

        System.out.println(testingCreatedService.getTitle());
    }

    @Test
    public void testGetProfessorById(){
        Optional<ProfessorDto> professorById = professorService.getProfessorById(3L); // Use @ToString in DTO to print Optional
        System.out.println(professorById);
    }

    @Test
    public void testGetAllProfessors(){
        List<ProfessorDto> allProfessor = professorService.getAllProfessor();
        System.out.println(allProfessor);
    }
}
