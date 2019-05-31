package com.java.spring.rabbit.queueconsumer.Listener;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.spring.rabbit.queueconsumer.DAL.Model.LoggingTable;
import com.java.spring.rabbit.queueconsumer.DAL.Repository.LoggingRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
@Component
public class DoctorListener {

    @RabbitListener(queues="${jsa.rabbitmq.queue.gettype}")
    public void receivedMessageGet(String msg) {
        System.out.println("Received Message: " + msg);

        LoggingTable record = DeserializeLogRecord(msg);
        System.out.println(record);

        if (record == null) return;
        record.setMessageType("RECORD GOTTEN");
        saveEntity(record);
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue.createdtype}")
    public void receivedMessageCreated(String msg) {
        System.out.println("Received Message: " + msg);

        LoggingTable record = DeserializeLogRecord(msg);
        System.out.println(record);

        if(record == null) return;
        record.setMessageType("RECORD CREATED");
        saveEntity(record);
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue.deletedtype}")
    public void receivedMessageDelete(String msg) {
        System.out.println("Received Message: " + msg);

        LoggingTable record = DeserializeLogRecord(msg);
        System.out.println(record);

        if(record == null) return;
        record.setMessageType("RECORD DELETED");

        saveEntity(record);
    }







    private LoggingRepository repository;

    @Autowired
    public void setBuilderRepository(LoggingRepository repository) {
        this.repository = repository;
    }

    public LoggingTable saveEntity(LoggingTable table) {

        if (table.getMessageText() == null || table.getMessageType() == null)
            return null;
        return repository.save(table);
    }

    private LoggingTable DeserializeLogRecord(String logRecord)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LoggingTable LoggerTableEntity = mapper.readValue(logRecord, LoggingTable.class);
            System.out.println(LoggerTableEntity);
            return LoggerTableEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
