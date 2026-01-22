package com.shikhilrane.project.collegeManagement.services.ServiceImplementation;

import com.shikhilrane.project.collegeManagement.dto.ProfessorDto;
import com.shikhilrane.project.collegeManagement.entities.ProfessorEntity;
import com.shikhilrane.project.collegeManagement.repositories.ProfessorRepository;
import com.shikhilrane.project.collegeManagement.services.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
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

    public boolean isExistByProfessorId(Long id){
        return professorRepository.existsById(id);
    }

    // Update Professor
    @Override
    public ProfessorDto updateProfessorInfo(Long id, ProfessorDto professorDto) {
        boolean existByProfessorId = isExistByProfessorId(id);
        if (!existByProfessorId) {
            return null;
        }
        ProfessorEntity mapped = modelMapper.map(professorDto, ProfessorEntity.class);
        mapped.setId(id);
        ProfessorEntity saved = professorRepository.save(mapped);
        return modelMapper.map(saved, ProfessorDto.class);
    }

    // Delete Professor
    @Override
    public boolean deleteProfessor(Long id) {
        boolean exist = isExistByProfessorId(id);
        if (!exist) {
            return false;
        } else {
            professorRepository.deleteById(id);
            return true;
        }
    }

    // Patch Professor
    @Override
    public ProfessorDto patchPrpfessor(Long id, Map<String, Object> updates) {
        boolean exist = isExistByProfessorId(id);
        if (!exist) {
            return null;
        }
        ProfessorEntity professorEntity = professorRepository.findById(id).get();
        updates.forEach((key,value) -> {
            Field requiredField = ReflectionUtils.getRequiredField(ProfessorEntity.class, key);
            requiredField.setAccessible(true);
            ReflectionUtils.setField(requiredField,professorEntity,value);
        });
        ProfessorEntity saved = professorRepository.save(professorEntity);
        return modelMapper.map(saved,ProfessorDto.class);
    }


}