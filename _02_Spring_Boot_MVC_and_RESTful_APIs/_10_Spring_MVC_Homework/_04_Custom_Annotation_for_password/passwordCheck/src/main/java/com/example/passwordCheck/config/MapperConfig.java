package com.example.passwordCheck.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig extends ModelMapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
