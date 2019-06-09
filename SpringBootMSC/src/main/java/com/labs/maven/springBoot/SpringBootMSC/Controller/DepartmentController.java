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
@RequestMapping("/service/department")
public class DepartmentController {

    private DepartmentService service;

    @Autowired
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Optional<Department>> getDepartment(@PathVariable Integer id){

        if (!service.getById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(service.getById(id));
        else {
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Department>> getAllDepartments(){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Department> create(@RequestBody Department dep){

        if (dep.getName() == null || dep.getFloor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(dep));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(dep));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Department> update(@RequestBody Department newDep, @PathVariable Integer id){

        if (newDep.getName() == null || newDep.getFloor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.updateObject(newDep, id));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.updateObject(newDep, id));
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
