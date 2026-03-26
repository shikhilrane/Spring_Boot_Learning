package com.shikhilrane.understandingMapping.mapstruct.service.serviceImplementations;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserDto;
import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.Contact;
import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import com.shikhilrane.understandingMapping.mapstruct.mappers.Mapper1;
import com.shikhilrane.understandingMapping.mapstruct.repositories.ContactRepository;
import com.shikhilrane.understandingMapping.mapstruct.repositories.UserRepository;
import com.shikhilrane.understandingMapping.mapstruct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ContactRepository contactRepository;

    private final Mapper1 mapper1 = Mappers.getMapper(Mapper1.class);

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

    @Override
    public Optional<UserResponseDto> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        Optional<Contact> contact = contactRepository.findById(id);

//        // Traditional Way
//        UserResponseDto userResponseDto = new UserResponseDto();
//        userResponseDto.setId(user.get().getId());
//        userResponseDto.setName(user.get().getUsername());
//        userResponseDto.setPassword(user.get().getPassword());
//        userResponseDto.setDob((DateTimeFormatter.ISO_LOCAL_DATE.format(user.get().getDateOfBirth())));
//        userResponseDto.setStatus(user.get().getStatus());

//        UserResponseDto userResponseDto = mapper1.mapUserEntityToUserResponseDTO(user);

//        return user.map(u -> mapper1.mapUserEntityToUserResponseDTO(u));

//        return user.map(user1 -> mapper1.mapUsernameOnly(user1));

        UserResponseDto userResponseDto = mapper1.mapUserEntityToUserAndContactResponseDTO(user.get(), contact.get());

        return Optional.of(userResponseDto);
    }

}
