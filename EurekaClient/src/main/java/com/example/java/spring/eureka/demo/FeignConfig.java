package com.example.java.spring.eureka.demo;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level LoggingLevel() {
        return Logger.Level.FULL;
    }
}
