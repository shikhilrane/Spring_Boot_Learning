package com.shikhilrane.understandingMapping.mapstruct.service;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;

import java.util.Optional;

public interface UserContactService {
    Optional<UserResponseDto> getUserById(Long id);
}
