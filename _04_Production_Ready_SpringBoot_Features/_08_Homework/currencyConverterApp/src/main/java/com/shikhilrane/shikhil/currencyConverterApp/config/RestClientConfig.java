package com.shikhilrane.shikhil.currencyConverterApp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${currencyService.base.url}")
    private String baseUrl;

    @Bean
    @Qualifier("currencyRestClient")
    public RestClient currencyRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}