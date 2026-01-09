package com.codingshuttle.fieldBasedDi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FieldBasedDiApplication implements CommandLineRunner {

	@Autowired
	private DBService dbService;

	public static void main(String[] args) {
		SpringApplication.run(FieldBasedDiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(dbService.getDataFromDB());
	}
}
