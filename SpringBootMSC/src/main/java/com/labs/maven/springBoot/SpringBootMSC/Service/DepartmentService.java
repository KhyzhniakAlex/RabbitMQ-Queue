package com.labs.maven.springBoot.SpringBootMSC.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.labs.maven.springBoot.SpringBootMSC.Messaging.Producer;
import com.labs.maven.springBoot.SpringBootMSC.Model.*;
import com.labs.maven.springBoot.SpringBootMSC.Repositories.DepartmentRepository;
import com.labs.maven.springBoot.SpringBootMSC.ServerExceptions.InvalidInfoException;
import com.labs.maven.springBoot.SpringBootMSC.ServerExceptions.ThereIsNoSuchItemException;
import com.labs.maven.springBoot.SpringBootMSC.ServiceInterfaces.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class DepartmentService implements ServiceInterface<Department> {

    private DepartmentRepository repository;

    @Autowired
    public void setDepartmentRepository(DepartmentRepository repository) {
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
    public Optional<Department> getById(Integer id) {
        Optional<Department> department = repository.findById(id);
        if (!department.isPresent()) {
            ExceptionMessage em = new ExceptionMessage();
            em.setGap(null);
            throw new ThereIsNoSuchItemException();
        }
        else {
            department.map(dep -> {
                if (!dep.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                }
                return dep;
            });
        }
        sendLog(department.get(), queueGetName);
        return department;
    }


    @Override
    public List<Department> getAll() {
        List<Department> departments = (List<Department>)repository.findAll();
        List<Department> newDepartments = new ArrayList<>();
        for (Department dep : departments) {
            repository.findById(dep.getId()).map(depat -> {
                if (depat.getPresenceFlag()) {
                    Set<Doctor> doctors = new HashSet<>();
                    for (Doctor doc : depat.getDoctors()) {
                        if (doc.getPresenceFlag())
                            doctors.add(doc);
                    }
                    depat.setDoctors(doctors);
                    newDepartments.add(depat);
                }
                return depat;
            });
        }
        return newDepartments;
    }

    @Override
    public Department saveObject(Department department) {

        ExceptionMessage em = new ExceptionMessage();

        if (department.getName() == null) {
            em.setGap("Incorrect NAME");
            throw new InvalidInfoException();
        }
        else if (department.getFloor() == null){
            em.setGap("Incorrect FLOOR");
            throw new InvalidInfoException();
        }
        else{
            sendLog(department, queueCreatedName);
            return repository.save(department);
        }
    }

    @Override
    public Department updateObject(Department newDep, Integer id) {

        ExceptionMessage em = new ExceptionMessage();

        if (newDep.getName() == null) {
            em.setGap("Incorrect NAME");
            throw new InvalidInfoException();
        }
        else if (newDep.getFloor() == null){
            em.setGap("Incorrect FLOOR");
            throw new InvalidInfoException();
        }
        else {
            return repository.findById(id)
                    .map(dep -> {
                        if (dep.getPresenceFlag()) {
                            dep.setName(newDep.getName());
                            dep.setFloor(newDep.getFloor());
                            return repository.save(dep);
                        }
                        else {
                            newDep.setId(id);
                            return repository.save(newDep);
                        }
                    })
                    .orElseGet(() -> {
                        newDep.setId(id);
                        return repository.save(newDep);
                    });
        }
    }

    @Override
    public boolean deleteObject(Integer id) {
        if (!repository.findById(id).isPresent()) {
            ExceptionMessage em = new ExceptionMessage();
            em.setGap(null);
            throw new ThereIsNoSuchItemException();
        } else {
            repository.findById(id).map(department -> {
                if (!department.getPresenceFlag()) {
                    ExceptionMessage em = new ExceptionMessage();
                    em.setGap(null);
                    throw new ThereIsNoSuchItemException();
                } else {
                    department.setPresenceFlag(false);
                    for(Doctor doc : department.getDoctors()) {
                        doc.setDepartment(null);
                    }
                    sendLog(department, queueDeletedName);
                    return repository.save(department);
                }
            });
        }
        return true;
    }




    private void sendLog(Department department, String queueName) {
        System.out.println("*******************");
        System.out.println("Sending message");
        LoggingTable logRecord = new LoggingTable();
        ObjectMapper mapper = new ObjectMapper();


        try {
            logRecord.setMessageText(mapper.writeValueAsString(department));
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
