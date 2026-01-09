package com.serviceLayer.service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }
}
// @Configuration -> marks a class as a source of Spring bean definitions
// @Bean -> tells Spring to manage the returned object as a bean in the application context.
// ModelMapper is used to convert any class object to another class object, commonly Entity to DTO while returning responses