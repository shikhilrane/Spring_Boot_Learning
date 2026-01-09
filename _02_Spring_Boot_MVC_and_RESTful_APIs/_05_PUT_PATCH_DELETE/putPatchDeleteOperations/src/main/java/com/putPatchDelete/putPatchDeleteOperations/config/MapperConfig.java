package com.putPatchDelete.putPatchDeleteOperations.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();   // Creates and returns a new ModelMapper instance
    }
}
// @Configuration -> marks a class as a source of Spring bean definitions
// @Bean -> tells Spring to manage the returned object as a bean in the application context.