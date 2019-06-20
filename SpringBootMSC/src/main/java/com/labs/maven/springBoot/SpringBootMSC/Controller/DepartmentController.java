package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Department;
import com.labs.maven.springBoot.SpringBootMSC.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private DepartmentService service;

    @Autowired
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Department> getDepartment(@PathVariable Integer id){
        return service.getById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Department> getAllDepartments(){
        return service.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Department> create(@RequestBody Department dep){
        return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(dep));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Department> update(@RequestBody Department newDep, @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newDep, id));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteObject(id));
    }
}
