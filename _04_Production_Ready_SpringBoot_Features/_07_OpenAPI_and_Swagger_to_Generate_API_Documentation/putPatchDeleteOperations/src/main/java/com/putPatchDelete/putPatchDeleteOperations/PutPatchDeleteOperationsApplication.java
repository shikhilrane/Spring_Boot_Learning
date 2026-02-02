package com.putPatchDelete.putPatchDeleteOperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PutPatchDeleteOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PutPatchDeleteOperationsApplication.class, args);
	}

}

/*
	Flow of project -
		1. SpringApplication.run()
		2. → IoC Container created
		3. → Component Scanning (finds @Controller, @Service, etc.)
		4. → DispatcherServlet is registered as a bean (via auto-configuration)
		5. → Embedded web server starts (Tomcat, Jetty, etc.)
		6. → Application is ready to receive requests
		7. HTTP Request comes in
		8. → DispatcherServlet handles it
		9. → Routes to @Controller
		10. → Calls Service
		11. → Calls Repository
		12. → Fetches from DB
		13. → Response sent back

		controller -> DTO -> service -> Entity -> Repository
*/