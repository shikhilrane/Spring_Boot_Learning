package com.codingshuttle.setterBasedDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SetterBasedDiApplication implements CommandLineRunner {

	@Autowired
	DBService dbService;

	public static void main(String[] args) {
		SpringApplication.run(SetterBasedDiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(dbService.getDataFromDB());
	}
}
