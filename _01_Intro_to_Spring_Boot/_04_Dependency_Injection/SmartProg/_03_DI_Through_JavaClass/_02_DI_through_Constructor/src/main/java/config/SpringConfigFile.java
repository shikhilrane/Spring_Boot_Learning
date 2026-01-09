package config;

import beans.Address;
import beans.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SpringConfigFile {
    @Bean (name = "toBeInjected")
    public Address createAddr(){
        Address addr = new Address(200, "Nashik", 12321);
        return addr;
    }

    @Bean (name = "inWhichInjected")
    public Student createStd(){
        Student std = new Student("Shikhil Rane", 231, createAddr());
        return std;
    }
}
