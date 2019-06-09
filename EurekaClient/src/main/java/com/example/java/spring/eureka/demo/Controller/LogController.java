package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LogController {

    private RestClient client;

    @Autowired
    public LogController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllDoctors(Model model, String error, String logout) {

        String logsStr = client.getAllLogs().getBody().toString();
        Log[] logs;
        if (logsStr != null) {
            logs = DeserializeList(logsStr);
            model.addAttribute("logs", logs);
        }
        return "logList";
    }


    private Log[] DeserializeList(String logsString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Log[] logs = mapper.readValue(logsString, Log[].class);
            return logs;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
