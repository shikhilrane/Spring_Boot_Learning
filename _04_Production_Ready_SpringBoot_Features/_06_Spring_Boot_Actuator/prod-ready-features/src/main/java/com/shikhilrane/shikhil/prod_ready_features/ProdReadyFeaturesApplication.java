package com.shikhilrane.shikhil.prod_ready_features;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProdReadyFeaturesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdReadyFeaturesApplication.class, args);
	}

}

// Always run TransformResponse of module 2, because we are using RestClient of that project