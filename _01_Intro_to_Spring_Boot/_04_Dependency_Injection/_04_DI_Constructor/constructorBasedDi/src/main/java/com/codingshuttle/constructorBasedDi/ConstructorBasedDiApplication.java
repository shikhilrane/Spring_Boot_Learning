package com.codingshuttle.constructorBasedDi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConstructorBasedDiApplication implements CommandLineRunner {

	private final DBService dbService;

	public ConstructorBasedDiApplication(DBService dbService) {
		this.dbService = dbService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ConstructorBasedDiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(dbService.getDataFromDB());
	}
}
