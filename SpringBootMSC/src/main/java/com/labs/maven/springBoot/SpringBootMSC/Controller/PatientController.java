package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Patient;
import com.labs.maven.springBoot.SpringBootMSC.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService service;

    @Autowired
    public void setPatientService(PatientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Patient> getPatient(@PathVariable Integer id){
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Patient> getAllPatients(){
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Patient> create(@RequestBody Patient pat){
        return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(pat));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Patient> update(@RequestBody Patient newPat, @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newPat, id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteObject(id));
    }
}
