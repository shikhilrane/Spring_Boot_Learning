package com.shikhilrane.understandingMapping.mapstruct.service;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserDto;
import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;

import java.util.Optional;

public interface UserService {

    UserDto createUser(UserDto userDto);

    Optional<UserDto> getById(Long id);
}
