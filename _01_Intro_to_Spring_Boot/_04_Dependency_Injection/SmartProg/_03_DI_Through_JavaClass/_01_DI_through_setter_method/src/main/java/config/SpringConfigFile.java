package config;

import beans.Address;
import beans.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SpringConfigFile {
    @Bean (name = "toBeInjected")
    public Address createAddr(){
        Address addr = new Address();
        addr.setHouseNo(89);
        addr.setCity("Indore");
        addr.setPincode(111111);
        return addr;
    }

    @Bean (name = "inWhichInjected")
    public Student createStd(){
        Student std = new Student();
        std.setName("Shikhil Kishor Rane");
        std.setRollNo(10);
        std.setAddress(createAddr());
        return std;
    }
}
