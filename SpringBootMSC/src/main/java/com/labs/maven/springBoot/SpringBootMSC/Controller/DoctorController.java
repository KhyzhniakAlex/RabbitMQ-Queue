package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Doctor;
import com.labs.maven.springBoot.SpringBootMSC.Service.DoctorService;
import javafx.application.Application;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
@RefreshScope
public class DoctorController {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private DoctorService service;

    @Autowired
    public void setDoctorService(DoctorService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Optional<Doctor>> getDoctor(@PathVariable Integer id){

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(service.getById(id));
        else {
            LOG.info("This is an info message(getOneDoctor)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Doctor>> getAllDoctors(){

        try {
            LOG.info("This is an info message(getAllDoctors)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Doctor> create(@RequestBody Doctor doc){

        LOG.info("This is an info message(createDoctor)");
        if (doc.getFirstName() == null || doc.getLastName() == null || doc.getAge() == null || doc.getSalary() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(doc));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(doc));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Doctor> update(@RequestBody Doctor newDoc, @PathVariable Integer id){

        LOG.info("This is an info message(updateDoctor)");
        if (newDoc.getFirstName() == null || newDoc.getLastName() == null || newDoc.getAge() == null || newDoc.getSalary() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.updateObject(newDoc, id));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newDoc, id));
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        else {
            LOG.info("This is an info message(deleteDoctor)");
            service.deleteObject(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
}
