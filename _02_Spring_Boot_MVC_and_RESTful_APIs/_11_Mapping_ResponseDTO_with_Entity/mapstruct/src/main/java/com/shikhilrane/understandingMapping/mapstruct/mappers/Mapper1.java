package com.shikhilrane.understandingMapping.mapstruct.mappers;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.Contact;
import com.shikhilrane.understandingMapping.mapstruct.entities.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;


@Mapper(componentModel = "spring")
public interface Mapper1 {

    @Mapping(source = "username", target = "name")
    @Mapping(source = "dateOfBirth", target = "dob")
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "status", target = "status", defaultValue = "INACTIVE")
    UserResponseDto mapUserEntityToUserResponseDTO(User user);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "username", target = "name")
    UserResponseDto mapUsernameOnly(User user);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "name")
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "user.dateOfBirth", target = "dob")
    @Mapping(source = "user.status", target = "status", defaultValue = "INACTIVE")
    @Mapping(source = "contact.mobileNumber", target = "mob", qualifiedByName = "MaskedPhone")
    @Mapping(source = "contact.email", target = "emailId")
    UserResponseDto mapUserEntityToUserAndContactResponseDTO(User user, Contact contact);

    @Named("MaskedPhone")
    static String getPhoneNumber(String phone){
        return "*****" + phone.substring(5);
    }
}
