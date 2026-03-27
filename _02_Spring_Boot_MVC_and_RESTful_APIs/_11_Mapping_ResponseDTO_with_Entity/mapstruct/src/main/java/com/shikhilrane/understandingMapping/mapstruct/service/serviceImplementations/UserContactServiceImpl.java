package com.shikhilrane.understandingMapping.mapstruct.service.serviceImplementations;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.Contact;
import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import com.shikhilrane.understandingMapping.mapstruct.mappers.Mapper1;
import com.shikhilrane.understandingMapping.mapstruct.repositories.ContactRepository;
import com.shikhilrane.understandingMapping.mapstruct.repositories.UserRepository;
import com.shikhilrane.understandingMapping.mapstruct.service.UserContactService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserContactServiceImpl implements UserContactService {

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    private final Mapper1 mapper1 = Mappers.getMapper(Mapper1.class);

    @Override
    public Optional<UserResponseDto> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        Optional<Contact> contact = contactRepository.findById(id);

//        // 1. Traditional Way
//        UserResponseDto userResponseDto = new UserResponseDto();
//        userResponseDto.setId(user.get().getId());
//        userResponseDto.setName(user.get().getUsername());
//        userResponseDto.setPassword(user.get().getPassword());
//        userResponseDto.setDob((DateTimeFormatter.ISO_LOCAL_DATE.format(user.get().getDateOfBirth())));
//        userResponseDto.setStatus(user.get().getStatus());


//        // 1.1 Without using Mapping()
//        UserResponseDto userResponseDto = mapper1.mapUserEntityToUserResponseDTO(user);
//        return Optional.of(userResponseDto);

//        // 1.2 With Mapping()
//        return user.map(u -> mapper1.mapUserEntityToUserResponseDTO(u));

//        // 2. To Ignore other fields that user don't pass
//        return user.map(user1 -> mapper1.mapUsernameOnly(user1));

        // 3. Combining both User and Contact
        UserResponseDto userResponseDto = mapper1.mapUserEntityToUserAndContactResponseDTO(user.get(), contact.get());
        return Optional.of(userResponseDto);
    }
}
