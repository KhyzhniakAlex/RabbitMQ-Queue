package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Messaging.Producer;
import com.example.java.spring.eureka.demo.Model.LoggingTable;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/amqp")
public class AmqpController {

    @Autowired
    Producer publisher;

    @RequestMapping("/send")
    public String sendMessage(){

        System.out.println("*******************");
        LoggingTable logRecord = new LoggingTable();
        logRecord.setMessageText("AmqpTesting");
        logRecord.setMessageType("le");
        ObjectMapper mapper = new ObjectMapper();

        //Convert object to JSON string and pretty print
        String jsonInString = null;
        try {
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInString);
        publisher.produceMsg("jsa.queue2", jsonInString);
        return "Successfully Msg Sent";
    }
}
