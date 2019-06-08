package com.example.java.spring.eureka.demo.Client;

import com.example.java.spring.eureka.demo.Model.Department;
import com.example.java.spring.eureka.demo.Model.Doctor;
import com.example.java.spring.eureka.demo.Model.Patient;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("lab")
@RestController
public interface RestClient {

    @GetMapping(value = "/doctor/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    List<Doctor> getAllDoctors();

    @PostMapping(value = "/doctor/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> createDoctor(@RequestBody Doctor doctor);

    @GetMapping(value = "/doctor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Doctor getOneDoctor(@PathVariable Integer id);

    @PostMapping(value = "/doctor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> updateDoctor(@PathVariable Integer id, @RequestBody Doctor newDoctor);

    @GetMapping(value = "/doctor/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Map<String, Boolean> deleteDoctor(@PathVariable Integer id);



    @GetMapping(value = "/department/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    List<Department> getAllDepartments();

    @PostMapping(value = "/department/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> createDepartment(@RequestBody Department department);

    @GetMapping(value = "/department/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Department getOneDepartment(@PathVariable Integer id);

    @PostMapping(value = "/department/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> updateDepartment(@PathVariable Integer id, @RequestBody Department newDepartment);

    @GetMapping(value = "/department/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Map<String, Boolean> deleteDepartment(@PathVariable Integer id);




    @GetMapping(value = "/patient/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    List<Patient> getAllPatients();

    @PostMapping(value = "/patient/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> createPatient(@RequestBody Patient patient);

    @GetMapping(value = "/patient/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Patient getOnePatient(@PathVariable Integer id);

    @PostMapping(value = "/patient/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Object> updatePatient(@PathVariable Integer id, @RequestBody Patient newPatient);

    @GetMapping(value = "/patient/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    Map<String, Boolean> deletePatient(@PathVariable Integer id);
}
