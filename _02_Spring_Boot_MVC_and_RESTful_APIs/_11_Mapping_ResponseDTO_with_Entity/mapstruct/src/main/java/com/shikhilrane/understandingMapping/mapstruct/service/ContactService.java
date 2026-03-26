package com.shikhilrane.understandingMapping.mapstruct.service;

import com.shikhilrane.understandingMapping.mapstruct.dto.ContactDto;

import java.util.Optional;

public interface ContactService {

    ContactDto createContact(ContactDto contactDto);

    Optional<ContactDto> getContactById(Long id);
}
