package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Patient;
import com.labs.maven.springBoot.SpringBootMSC.Service.PatientService;
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
@RequestMapping("/patient")
@RefreshScope
public class PatientController {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private PatientService service;

    @Autowired
    public void setPatientService(PatientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<Optional<Patient>> getPatient(@PathVariable Integer id){

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(service.getById(id));
        else {
            LOG.info("This is an info message(getOnePatient)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody ResponseEntity<List<Patient>> getAllPatients(){

        try {
            LOG.info("This is an info message(getAllPatients)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Patient> create(@RequestBody Patient pat){

        LOG.info("This is an info message(createPatient)");
        if (pat.getFirstName() == null || pat.getLastName() == null || pat.getAge() == null || pat.getDiagnosis() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(pat));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(pat));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Patient> update(@RequestBody Patient newPat, @PathVariable Integer id){

        LOG.info("This is an info message(updatePatient)");
        if (newPat.getFirstName() == null || newPat.getLastName() == null || newPat.getAge() == null || newPat.getDiagnosis() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.updateObject(newPat, id));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newPat, id));
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        else {
            LOG.info("This is an info message(deletePatient)");
            service.deleteObject(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
}
