package com.example.java.spring.eureka.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
@RefreshScope
public class EurekaServerApplication {

    @Value("${message: Hello default}")
    private String greeting;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String greet(){
        return greeting;
    }



    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
