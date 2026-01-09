package com.example.passwordCheck.service;

import com.example.passwordCheck.dto.DepartmentDto;
import com.example.passwordCheck.entity.DepartmentEntity;
import com.example.passwordCheck.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    // 1. Get by ID
    public Optional<DepartmentDto> getDepartmentById(Long id) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(id);
        return departmentEntity.map(departmentEntity1 -> modelMapper.map(departmentEntity1, DepartmentDto.class));
    }

    // 2. Get All
    public List<DepartmentDto> getAllDepartment() {
        List<DepartmentEntity> departmentRepositoryAll = departmentRepository.findAll();
        return departmentRepositoryAll
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    // 3. Post
    public DepartmentDto createNewDepartment(DepartmentDto departmentDto) {
        DepartmentEntity convtIncomingDtoToEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        DepartmentEntity savedToRepo = departmentRepository.save(convtIncomingDtoToEntity);
        return modelMapper.map(savedToRepo, DepartmentDto.class);
    }

    public boolean isExistById(Long id){
        return departmentRepository.existsById(id);
    }

    // 4. Put
    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        boolean isExist = isExistById(id);
        if (!isExist){
            return null;
        }
        DepartmentEntity convtIncomingDtoToEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        convtIncomingDtoToEntity.setId(id);
        DepartmentEntity saveUpdate = departmentRepository.save(convtIncomingDtoToEntity);
        return modelMapper.map(saveUpdate, DepartmentDto.class);
    }

    // 5. Delete
    public boolean deleteDepartmentById(Long id) {
        boolean isExist = isExistById(id);
        if (!isExist){
            return false;
        }
        else {
            departmentRepository.deleteById(id);
            return true;
        }
    }

    // 6. Patch
    public DepartmentDto partialUpdate(Long id, Map<String, Object> updates) {
        boolean isExist = isExistById(id);
        if (!isExist){
            return null;
        }
        DepartmentEntity departmentEntity = departmentRepository.findById(id).get();
        updates.forEach((key, value) -> {
            Field fieldToUpdate = ReflectionUtils.getRequiredField(DepartmentEntity.class, key);
            fieldToUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToUpdate, departmentEntity, value);
        });
        DepartmentEntity savedEntity = departmentRepository.save(departmentEntity);
        return modelMapper.map(savedEntity, DepartmentDto.class);
    }
}
