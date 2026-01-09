package com.codingshuttle.setterBasedDi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "deploy.envt", havingValue = "production")
public class ProdDB implements DB{
    public String getData(){
        return "Production Data";
    }
}
