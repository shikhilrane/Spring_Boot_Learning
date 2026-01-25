package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.CreateProfessorDto;
import com.shikhilrane.project.collegeManagement.dto.ProfessorResponseDto;
import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import com.shikhilrane.project.collegeManagement.repositories.ProfessorRepository;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepo;

    @Override
    public ProfessorResponseDto create(CreateProfessorDto dto) {
        ProfessorEntity p = new ProfessorEntity();
        p.setTitle(dto.getTitle());
        ProfessorEntity saved = professorRepo.save(p);

        return new ProfessorResponseDto(saved.getId(), saved.getTitle());
    }
}
