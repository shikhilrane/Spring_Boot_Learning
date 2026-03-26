package com.shikhilrane.understandingMapping.mapstruct.service.serviceImplementations;

import com.shikhilrane.understandingMapping.mapstruct.dto.ContactDto;
import com.shikhilrane.understandingMapping.mapstruct.entities.Contact;
import com.shikhilrane.understandingMapping.mapstruct.repositories.ContactRepository;
import com.shikhilrane.understandingMapping.mapstruct.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Override
    public ContactDto createContact(ContactDto contactDto) {
        Contact mapDtoWithEntity = modelMapper.map(contactDto, Contact.class);
        Contact saveToRepostory = contactRepository.save(mapDtoWithEntity);
        return modelMapper.map(saveToRepostory, ContactDto.class);
    }

    @Override
    public Optional<ContactDto> getContactById(Long id) {
        Optional<Contact> byId = contactRepository.findById(id);
        return byId.map(byId1 -> modelMapper.map(byId1, ContactDto.class));
    }
}
