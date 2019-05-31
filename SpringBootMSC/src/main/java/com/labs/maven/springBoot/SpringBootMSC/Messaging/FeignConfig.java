package com.labs.maven.springBoot.SpringBootMSC.Messaging;

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
