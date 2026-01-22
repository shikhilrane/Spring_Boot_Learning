package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import com.shikhilrane.project.collegeManagement.repositories.ProfessorRepository;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ModelMapper modelMapper;

    // Post
    @Override
    public ProfessorDto creatingProfessor(ProfessorDto professorDto) {
        ProfessorEntity mapped = modelMapper.map(professorDto, ProfessorEntity.class);
        ProfessorEntity saved = professorRepository.save(mapped);
        return modelMapper.map(saved, ProfessorDto.class);
    }

    // Get byId
    @Override
    public Optional<ProfessorDto> getProfessorById(Long id) {
        Optional<ProfessorEntity> byId = professorRepository.findById(id);
        return byId.map(byId1 -> modelMapper.map(byId1, ProfessorDto.class));
    }

    // Get All Professor
    @Override
    public List<ProfessorDto> getAllProfessor() {
        List<ProfessorEntity> all = professorRepository.findAll();
        return all
                .stream()
                .map(allProfessor -> modelMapper.map(allProfessor, ProfessorDto.class))
                .collect(Collectors.toList());
    }


}