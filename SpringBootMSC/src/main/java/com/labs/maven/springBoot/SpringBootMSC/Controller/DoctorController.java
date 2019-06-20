package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Doctor;
import com.labs.maven.springBoot.SpringBootMSC.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorService service;

    @Autowired
    public void setDoctorService(DoctorService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Doctor> getDoctor(@PathVariable Integer id){
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Doctor> getAllDoctors(){
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Doctor> create(@RequestBody Doctor doc){
        return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(doc));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Doctor> update(@RequestBody Doctor newDoc, @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newDoc, id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteObject(id));
    }
}
