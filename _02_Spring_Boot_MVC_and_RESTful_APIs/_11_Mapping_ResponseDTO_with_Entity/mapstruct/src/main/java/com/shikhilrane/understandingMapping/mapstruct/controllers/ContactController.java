package com.shikhilrane.understandingMapping.mapstruct.controllers;

import com.shikhilrane.understandingMapping.mapstruct.dto.ContactDto;
import com.shikhilrane.understandingMapping.mapstruct.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping(path = "/saveContact")
    public ResponseEntity<ContactDto> creatingContact(@RequestBody ContactDto contactDto){
        ContactDto contactDto1 = contactService.createContact(contactDto);
        return new ResponseEntity<>(contactDto1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{getContactById}")
    public ResponseEntity<ContactDto> getContact(@PathVariable (name = "getContactById") Long id){
        Optional<ContactDto> dto = contactService.getContactById(id);
        return dto
                .map(dto1 -> ResponseEntity.ok(dto1))
                .orElse(ResponseEntity.notFound().build());
    }
}
