package com.shikhilrane.understandingMapping.mapstruct.mappers;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface Mapper1 {

    UserResponseDto mapUserEntityToUserResponseDTO(Optional<User> user);
}
