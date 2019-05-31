package com.example.java.spring.eureka.demo.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class ConfigController {
    @Value("${message: Hello default}")
    private String greeting;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String greet(){
        return greeting;
    }
}
