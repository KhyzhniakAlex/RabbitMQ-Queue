package com.labs.maven.springBoot.SpringBootMSC.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.maven.springBoot.SpringBootMSC.Messaging.Producer;
import com.labs.maven.springBoot.SpringBootMSC.Model.Department;
import com.labs.maven.springBoot.SpringBootMSC.Model.Doctor;
import com.labs.maven.springBoot.SpringBootMSC.Model.ExceptionMessage;
import com.labs.maven.springBoot.SpringBootMSC.Model.LoggingTable;
import com.labs.maven.springBoot.SpringBootMSC.Repositories.DoctorRepository;
import com.labs.maven.springBoot.SpringBootMSC.ServerExceptions.InvalidInfoException;
import com.labs.maven.springBoot.SpringBootMSC.ServerExceptions.ThereIsNoSuchItemException;
import com.labs.maven.springBoot.SpringBootMSC.ServiceInterfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService implements ServiceInterface<Doctor> {

    private DoctorRepository repository;

    @Autowired
    public void setDoctorRepository(DoctorRepository repository) {
        this.repository = repository;
    }

    @Autowired
    Producer publisher;

    @Value("${jsa.rabbitmq.queue.createdtype}")
    String queueCreatedName;

    @Value("${jsa.rabbitmq.queue.gettype}")
    String queueGetName;

    @Value("${jsa.rabbitmq.queue.deletedtype}")
    String queueDeletedName;



    @Override
    public Optional<Doctor> getById(Integer id) {
        Optional<Doctor> doc = repository.findById(id);
        if (!doc.isPresent()) {
            ExceptionMessage em = new ExceptionMessage();
            em.setGap(null);
            throw new ThereIsNoSuchItemException();
        } else {
            doc.map(doctor -> {
                if (!doctor.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                }
                return doctor;
            });
        }
        sendLog(doc.get(), queueGetName);
        return doc;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> doctors = (List<Doctor>)repository.findAll();
        List<Doctor> newDoctors = new ArrayList<>();
        for (Doctor doc : doctors) {
            repository.findById(doc.getId()).map(doctor -> {
                if (doctor.getPresenceFlag())
                    newDoctors.add(doctor);
                return doctor;
            });
        }
        return newDoctors;
    }

    @Override
    public Doctor saveObject(Doctor doc) {

        ExceptionMessage em = new ExceptionMessage();

        if (doc.getFname() == null) {
            em.setGap("Incorrect FNAME");
            throw new InvalidInfoException();
        }
        else if (doc.getSurname() == null){
            em.setGap("Incorrect SURNAME");
            throw new InvalidInfoException();
        }
        else if (doc.getAge() == null) {
            em.setGap("Incorrect AGE");
            throw new InvalidInfoException();
        }
        else if (doc.getSalary() == null) {
            em.setGap("Incorrect SALARY");
            throw new InvalidInfoException();
        } else{
            sendLog(doc, queueCreatedName);
            return repository.save(doc);
        }
    }

    @Override
    public Doctor updateObject(Doctor newDoc, Integer id) {

        ExceptionMessage em = new ExceptionMessage();

        if (newDoc.getFname() == null) {
            em.setGap("Incorrect FNAME");
            throw new InvalidInfoException();
        }
        else if (newDoc.getSurname() == null){
            em.setGap("Incorrect SURNAME");
            throw new InvalidInfoException();
        }
        else if (newDoc.getAge() == null) {
            em.setGap("Incorrect AGE");
            throw new InvalidInfoException();
        }
        else if (newDoc.getSalary() == null) {
            em.setGap("Incorrect SALARY");
            throw new InvalidInfoException();
        } else {
            return repository.findById(id)
                    .map(doc -> {
                        if (doc.getPresenceFlag()) {
                            doc.setFname(newDoc.getFname());
                            doc.setSurname(newDoc.getSurname());
                            doc.setAge(newDoc.getAge());
                            doc.setSalary(newDoc.getSalary());
                            return repository.save(doc);
                        } else {
                            newDoc.setId(id);
                            return repository.save(newDoc);
                        }
                    })
                    .orElseGet(() -> {
                        newDoc.setId(id);
                        return repository.save(newDoc);
                    });
        }
    }

    @Override
    public void deleteObject(Integer id) {
        if (!repository.findById(id).isPresent()) {
            ExceptionMessage em = new ExceptionMessage();
            em.setGap(null);
            throw new ThereIsNoSuchItemException();
        } else {
            repository.findById(id).map(doc -> {
                if (!doc.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                } else {
                    doc.setPresenceFlag(false);
                    sendLog(doc, queueDeletedName);
                    return repository.save(doc);
                }
            });
        }
    }




    private void sendLog(Doctor doctor, String queueName) {
        System.out.println("*******************");
        System.out.println("Sending message");
        LoggingTable logRecord = new LoggingTable();
        ObjectMapper mapper = new ObjectMapper();


        try {
            logRecord.setMessageText(mapper.writeValueAsString(doctor));
            logRecord.setEntityName(Doctor.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String loggerRecordString = null;
        try {
            loggerRecordString = mapper.writeValueAsString(logRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(loggerRecordString);
        publisher.produceMsg(loggerRecordString, queueName);
    }
}
