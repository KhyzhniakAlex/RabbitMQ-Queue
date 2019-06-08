package com.labs.maven.springBoot.SpringBootMSC.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.maven.springBoot.SpringBootMSC.Messaging.Producer;
import com.labs.maven.springBoot.SpringBootMSC.Model.Patient;
import com.labs.maven.springBoot.SpringBootMSC.Model.ExceptionMessage;
import com.labs.maven.springBoot.SpringBootMSC.Model.LoggingTable;
import com.labs.maven.springBoot.SpringBootMSC.Repositories.PatientRepository;
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
public class PatientService implements ServiceInterface<Patient> {

    private PatientRepository repository;

    @Autowired
    public void setPatientRepository(PatientRepository repository) {
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
    public Optional<Patient> getById(Integer id) {
        Optional<Patient> pat = repository.findById(id);
        if (!pat.isPresent()) {
            ExceptionMessage em = new ExceptionMessage();
            em.setGap(null);
            throw new ThereIsNoSuchItemException();
        } else {
            pat.map(patient -> {
                if (!patient.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                }
                return patient;
            });
        }
        sendLog(pat.get(), queueGetName);
        return pat;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> patients = (List<Patient>)repository.findAll();
        List<Patient> newPatients = new ArrayList<>();
        for (Patient pat : patients) {
            repository.findById(pat.getId()).map(patient -> {
                if (patient.getPresenceFlag())
                    newPatients.add(patient);
                return patient;
            });
        }
        return newPatients;
    }

    @Override
    public Patient saveObject(Patient pat) {

        ExceptionMessage em = new ExceptionMessage();

        if (pat.getFirstName() == null) {
            em.setGap("Incorrect First name");
            throw new InvalidInfoException();
        }
        else if (pat.getLastName() == null){
            em.setGap("Incorrect Last name");
            throw new InvalidInfoException();
        }
        else if (pat.getAge() == null) {
            em.setGap("Incorrect AGE");
            throw new InvalidInfoException();
        }
        else if (pat.getDiagnosis() == null) {
            em.setGap("Incorrect Diagnosis");
            throw new InvalidInfoException();
        } else{
            sendLog(pat, queueCreatedName);
            return repository.save(pat);
        }
    }

    @Override
    public Patient updateObject(Patient newPat, Integer id) {

        ExceptionMessage em = new ExceptionMessage();

        if (newPat.getFirstName() == null) {
            em.setGap("Incorrect First name");
            throw new InvalidInfoException();
        }
        else if (newPat.getLastName() == null){
            em.setGap("Incorrect Last name");
            throw new InvalidInfoException();
        }
        else if (newPat.getAge() == null) {
            em.setGap("Incorrect AGE");
            throw new InvalidInfoException();
        }
        else if (newPat.getDiagnosis() == null) {
            em.setGap("Incorrect Diagnosis");
            throw new InvalidInfoException();
        } else {
            return repository.findById(id)
                    .map(pat -> {
                        if (pat.getPresenceFlag()) {
                            pat.setFirstName(newPat.getFirstName());
                            pat.setLastName(newPat.getLastName());
                            pat.setAge(newPat.getAge());
                            pat.setDiagnosis(newPat.getDiagnosis());
                            return repository.save(pat);
                        } else {
                            newPat.setId(id);
                            return repository.save(newPat);
                        }
                    })
                    .orElseGet(() -> {
                        newPat.setId(id);
                        return repository.save(newPat);
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
            repository.findById(id).map(pat -> {
                if (!pat.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                } else {
                    pat.setPresenceFlag(false);
                    sendLog(pat, queueDeletedName);
                    return repository.save(pat);
                }
            });
        }
    }




    private void sendLog(Patient patient, String queueName) {
        System.out.println("*******************");
        System.out.println("Sending message");
        LoggingTable logRecord = new LoggingTable();
        ObjectMapper mapper = new ObjectMapper();


        try {
            logRecord.setMessageText(mapper.writeValueAsString(patient));
            logRecord.setEntityName(Patient.class.getName());
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
