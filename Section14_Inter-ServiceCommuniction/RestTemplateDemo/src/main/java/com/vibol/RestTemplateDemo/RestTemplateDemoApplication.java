package com.vibol.RestTemplateDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestTemplateDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestTemplateDemoApplication.class, args);
    }

    // 1. Define the RestTemplate bean here so Spring can manage it
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}