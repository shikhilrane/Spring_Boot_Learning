package com.shikhilrane.shikhil.SecurityApp;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityAppApplicationTests {

    @Autowired
    private JwtService jwtService;

	@Test
	void contextLoads() {
	}

    @Test
    void getIdOfUser(){
        User user = new User(4L, "skr@mail.com", "1234", "Shikhil");   // Created a user

        String token = jwtService.generateAccessToken(user);                     // Created a token using generateUserToken() from JwtService

        System.out.println(token);

        Long id  = jwtService.getUserIdFromToken(token);                       // get id by passing a created token from getUserIdFromToken() from JwtService

        System.out.println(id);
    }
}
