package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.maven.springBoot.SpringBootMSC.Messaging.Producer;
import com.labs.maven.springBoot.SpringBootMSC.Model.LoggingTable;
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
