package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private RestClient client;

    @Autowired
    public LogController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDoctors() {

        ModelAndView model = new ModelAndView("logList");

        List<Log> logs = client.getAll();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            model.addObject("error", "You don't have permissions for this page");
            return model;
        }
        model.addObject("error", "");
        model.addObject("logs", logs);
        return model;
    }
}
