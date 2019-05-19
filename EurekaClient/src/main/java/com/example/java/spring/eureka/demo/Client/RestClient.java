package com.example.java.spring.eureka.demo.Client;

import com.example.java.spring.eureka.demo.Model.Department;
import com.example.java.spring.eureka.demo.Model.Doctor;
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

    @GetMapping(value = "/doctor/")
    List<Doctor> getAllDoctors();

    @PostMapping(value = "/doctor/")
    ResponseEntity<Object> createDoctor(@RequestBody Doctor doctor);

    @GetMapping(value = "/doctor/{id}")
    Doctor getOneDoctor(@PathVariable Integer id);

    @PostMapping(value = "/doctor/{id}")
    ResponseEntity<Object> updateDoctor(@PathVariable Integer id, @RequestBody Doctor newDoctor);

    @GetMapping(value = "/doctor/delete/{id}")
    Map<String, Boolean> deleteDoctor(@PathVariable Integer id);



    @GetMapping(value = "/department/")
    List<Department> getAllDepartments();

    @PostMapping(value = "/department/")
    ResponseEntity<Object> createDepartment(@RequestBody Department department);

    @GetMapping(value = "/department/{id}")
    Department getOneDepartment(@PathVariable Integer id);

    @PostMapping(value = "/department/{id}")
    ResponseEntity<Object> updateDepartment(@PathVariable Integer id, @RequestBody Department newDepartment);

    @GetMapping(value = "/department/delete/{id}")
    Map<String, Boolean> deleteDepartment(@PathVariable Integer id);
}
