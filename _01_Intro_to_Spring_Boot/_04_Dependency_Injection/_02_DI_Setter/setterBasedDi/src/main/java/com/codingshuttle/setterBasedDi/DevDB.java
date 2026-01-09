package com.codingshuttle.setterBasedDi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "deploy.envt", havingValue = "development")
public class DevDB implements DB{
    public String getData(){
        return "Development Data";
    }
}
