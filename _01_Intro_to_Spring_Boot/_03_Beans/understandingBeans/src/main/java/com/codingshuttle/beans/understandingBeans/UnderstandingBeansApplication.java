package com.codingshuttle.beans.understandingBeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class UnderstandingBeansApplication {	// 2.0 CommandLineRunner to implement non-static method (i.e. eatApple()) with run() method

	// 2.1
//	@Autowired		// This Annotation will inject Apple's object into this main class by creating its object internally
//	Apple apple;

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(UnderstandingBeansApplication.class, args);

		// 1.
//		Apple apple = new Apple();	// Traditional way to directly creating an Object of Apple class (without @Autowired, @Component)
//		apple.eatApple();

		// 3.
		Apple apl = context.getBean(Apple.class);
		apl.eatApple();
	}

	// 2.2
//	@Override
//	public void run(String... args) throws Exception {
//		apple.eatApple();
//	}
}
