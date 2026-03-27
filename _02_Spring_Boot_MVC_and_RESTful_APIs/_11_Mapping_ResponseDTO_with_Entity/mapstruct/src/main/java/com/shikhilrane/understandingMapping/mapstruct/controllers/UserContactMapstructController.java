package com.shikhilrane.understandingMapping.mapstruct.controllers;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserResponseDto;
import com.shikhilrane.understandingMapping.mapstruct.service.UserContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "user_contact")
@RequiredArgsConstructor
public class UserContactMapstructController {

    private final UserContactService userContactService;

    @GetMapping(path = "/{getById}")
    public ResponseEntity<UserResponseDto> getUserrById(@PathVariable(name = "getById") Long id){
        Optional<UserResponseDto> getData = userContactService.getUserById(id);
        return getData
                .map(getData1 -> ResponseEntity.ok(getData1))
                .orElse(ResponseEntity.notFound().build());
    }
}