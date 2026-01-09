package com.codingshuttle.beans.understandingBeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Marks the class as a source of bean definitions for the Spring IoC container.
public class AppConfig {
    @Bean   // Registers the return value of this method as a Spring bean in the application context.
    Apple getApple(){
        return new Apple();
    }
}
