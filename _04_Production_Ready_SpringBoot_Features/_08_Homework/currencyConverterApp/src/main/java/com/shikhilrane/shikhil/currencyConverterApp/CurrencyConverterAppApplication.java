package com.shikhilrane.shikhil.currencyConverterApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyConverterAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConverterAppApplication.class, args);
	}

}

// Hit the url : http://localhost:8080/currency/convert?from=INR&to=USD&units=500 (We can interchange the currencies)

/*
    1. Use the currency API to build your own API that converts one currency to another.
        Use this API : https://app.freecurrencyapi.com/request-playground and
        build this API : http://localhost:8080/convertCurrency?fromCurrency=INR&toCurrency=USD&units=500
        This will convert INR 500 TO USD
    2. Add logs in all the services for code you have created so far.
    3. Create auding in all the entities you have encountered so far.
    4. Add OpenAPI and Swagger UI in your project
*/