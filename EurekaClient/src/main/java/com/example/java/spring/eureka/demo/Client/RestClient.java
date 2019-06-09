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
import java.util.Optional;

@FeignClient("lab")
@RestController
public interface RestClient {

    @GetMapping(value = "/department/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    @ResponseBody ResponseEntity<List<Department>> getAllDepartments();

    @GetMapping(value = "/department/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    @ResponseBody ResponseEntity<Optional<Department>> getOneDepartment(@PathVariable Integer id);

    @PostMapping(value = "/department/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Department> createDepartment(@RequestBody Department department);

    @PostMapping(value = "/department/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department newDepartment);

    @GetMapping(value = "/department/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Boolean> deleteDepartment(@PathVariable Integer id);





    @GetMapping(value = "/doctor/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<List<Doctor>> getAllDoctors();

    @GetMapping(value = "/doctor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Optional<Doctor>> getOneDoctor(@PathVariable Integer id);

    @PostMapping(value = "/doctor/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor);

    @PostMapping(value = "/doctor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Doctor> updateDoctor(@PathVariable Integer id, @RequestBody Doctor newDoctor);

    @GetMapping(value = "/doctor/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Boolean> deleteDoctor(@PathVariable Integer id);





    @GetMapping(value = "/patient/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<List<Patient>> getAllPatients();

    @GetMapping(value = "/patient/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Optional<Patient>> getOnePatient(@PathVariable Integer id);

    @PostMapping(value = "/patient/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Department> createPatient(@RequestBody Patient patient);

    @PostMapping(value = "/patient/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Department> updatePatient(@PathVariable Integer id, @RequestBody Patient newPatient);

    @GetMapping(value = "/patient/delete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<Boolean> deletePatient(@PathVariable Integer id);




    @GetMapping(value = "/log/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Headers("Content-Type: application/json")
    ResponseEntity<?> getAllLogs();
}
