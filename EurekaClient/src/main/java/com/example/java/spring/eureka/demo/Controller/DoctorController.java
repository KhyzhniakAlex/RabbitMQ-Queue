package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Department;
import com.example.java.spring.eureka.demo.Model.Doctor;
import com.example.java.spring.eureka.demo.Model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class DoctorController {

    private RestClient client;

    @Autowired
    public DoctorController(RestClient client) {
        this.client = client;
    }

    @GetMapping("rest/doctor")
    public List<Doctor> getAllDoctors() {
        return client.getAllDoctors();
    }

    @GetMapping("rest/doctor/{id}")
    public Doctor getOneDoctor(@PathVariable Integer id) {
        return client.getOneDoctor(id);
    }

    @PostMapping("rest/doctor")
    public ResponseEntity<Object> createDoctor(@Valid @RequestBody Doctor doctor) {
        return client.createDoctor(doctor);
    }

    @PostMapping("rest/doctor/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable Integer id, @RequestBody Doctor newDoctor) {
        return client.updateDoctor(id, newDoctor);
    }

    @GetMapping("rest/doctor/delete/{id}")
    public Map<String, Boolean> deleteDoctor(@PathVariable Integer id) {
        return client.deleteDoctor(id);
    }




    private Doctor[] DeserializeDoctorList(String doctorString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(doctorString, Doctor[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private Patient[] DeserializePatientList(String patientString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(patientString, Patient[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private Doctor Deserialize(String doctorString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(doctorString, Doctor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
