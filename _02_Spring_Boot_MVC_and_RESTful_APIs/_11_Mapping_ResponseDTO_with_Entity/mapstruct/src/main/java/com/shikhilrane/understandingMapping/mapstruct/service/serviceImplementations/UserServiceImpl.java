package com.shikhilrane.understandingMapping.mapstruct.service.serviceImplementations;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import com.shikhilrane.understandingMapping.mapstruct.repositories.UserRepository;
import com.shikhilrane.understandingMapping.mapstruct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = modelMapper.map(userDto, User.class);
        User save = userRepository.save(savedUser);
        return modelMapper.map(save,UserDto.class);
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.map(byId1 -> modelMapper.map(byId1,UserDto.class));
    }
}
