package com.shikhilrane.understandingMapping.mapstruct.controllers;

import com.shikhilrane.understandingMapping.mapstruct.dto.UserDto;
import com.shikhilrane.understandingMapping.mapstruct.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/createUser")
    public ResponseEntity<UserDto> creatingUser(@RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/fromUserDto/{gettingById}")
    public ResponseEntity<UserDto> getId(@PathVariable (name = "gettingById") Long id){
        Optional<UserDto> gotuser = userService.getById(id);
        return gotuser
                .map(gotuser1 -> ResponseEntity.ok(gotuser1))
                .orElse(ResponseEntity.notFound().build());
    }
}
