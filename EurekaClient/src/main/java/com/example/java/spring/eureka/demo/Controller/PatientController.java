package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class PatientController {

    private RestClient client;

    @Autowired
    public PatientController(RestClient client) {
        this.client = client;
    }

    @GetMapping("rest/patient")
    public List<Patient> getAllPatients() {
        return client.getAllPatients();
    }

    @GetMapping("rest/patient/{id}")
    public Patient getOnePatient(@PathVariable Integer id) {
        return client.getOnePatient(id);
    }

    @PostMapping("rest/patient")
    public ResponseEntity<Object> createPatient(@Valid @RequestBody Patient patient) {
        return client.createPatient(patient);
    }

    @PostMapping("rest/patient/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable Integer id, @RequestBody Patient newPatient) {
        return client.updatePatient(id, newPatient);
    }

    @GetMapping("rest/patient/delete/{id}")
    public Map<String, Boolean> deletePatient(@PathVariable Integer id) {
        return client.deletePatient(id);
    }
}
