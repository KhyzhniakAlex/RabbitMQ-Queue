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
@RequestMapping("/service/doctor")
public class DoctorController {

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
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Doctor>> getAllDoctors(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Doctor> create(@RequestBody Doctor doc){

        if (doc.getFirstName() == null || doc.getLastName() == null || doc.getAge() == null || doc.getSalary() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(doc));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(doc));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Doctor> update(@RequestBody Doctor newDoc, @PathVariable Integer id){

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
            service.deleteObject(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
}
