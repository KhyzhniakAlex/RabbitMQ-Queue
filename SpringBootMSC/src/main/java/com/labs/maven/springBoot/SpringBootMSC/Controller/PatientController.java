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

    /*@Value("${message_patient:Patient}")
    private String message;

    @RequestMapping("/message")
    public String getPatientMessage() {
        LOG.info("This is an info message(messagePatient)");
        return message;
    }*/



    private PatientService service;

    @Autowired
    public void setPatientService(PatientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Patient> getPatient(@PathVariable Integer id){
        LOG.info("This is an info message(getOnePatient)");
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Patient> getAllPatients(){
        LOG.info("This is an info message(getAllPatients)");
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody Patient pat){

        LOG.info("This is an info message(createPatient)");
        if (pat.getFirstName() == null || pat.getLastName() == null || pat.getAge() == null || pat.getDiagnosis() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(pat));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(pat));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody Patient newPat, @PathVariable Integer id){

        LOG.info("This is an info message(updateDoctor)");
        if (newPat.getFirstName() == null || newPat.getLastName() == null || newPat.getAge() == null || newPat.getDiagnosis() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.updateObject(newPat, id));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newPat, id));
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Map<String, Boolean> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        service.getById(id).orElseThrow(() -> new ResourceNotFoundException(""));
        service.deleteObject(id);
        LOG.info("This is an info message(deletePatient)");
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete", Boolean.TRUE);
        return response;
    }
}
