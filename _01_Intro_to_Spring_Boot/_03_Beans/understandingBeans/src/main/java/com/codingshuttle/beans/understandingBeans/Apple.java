package com.codingshuttle.beans.understandingBeans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

//@Component    // The @Component annotation marks a Java class for Spring's component scanning to automatically detect, instantiate, and register it as a bean in the application context, (Commenting this because we have to execute AppConfig (3rd way) in main class so that this class won't interfere.)
public class Apple {
    void eatApple(){
        System.out.println("I am eating an apple");
    }

    @PostConstruct  // We haven't called this method in main method, and it will run immediately after the construction of object in IOC container
    void applePostConstruct(){
        System.out.println("Apple is post constructing");
    }

    @PreDestroy     // We haven't called this method in main method, and it will run before the destroy of object in IOC container
    void applePreDestroy(){
        System.out.println("Apple is Pre-Destroyed");
    }
}