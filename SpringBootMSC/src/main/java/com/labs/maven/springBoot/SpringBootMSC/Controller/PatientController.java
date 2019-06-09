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
@RequestMapping("/service/patient")
public class PatientController {

    private PatientService service;

    @Autowired
    public void setPatientService(PatientService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Optional<Patient>> getPatient(@PathVariable Integer id){

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(service.getById(id));
        else {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Patient>> getAllPatients(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Patient> create(@RequestBody Patient pat){

        if (pat.getFirstName() == null || pat.getLastName() == null || pat.getAge() == null || pat.getDiagnosis() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(pat));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(pat));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Patient> update(@RequestBody Patient newPat, @PathVariable Integer id){

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
            service.deleteObject(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
}
