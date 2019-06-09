package com.labs.maven.springBoot.SpringBootMSC.Controller;

import com.labs.maven.springBoot.SpringBootMSC.Model.Department;
import com.labs.maven.springBoot.SpringBootMSC.Service.DepartmentService;
import javafx.application.Application;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

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
            LOG.info("This is an info message(getOneDepartment)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getById(id));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Department>> getAllDepartments(){

        try {
            LOG.info("This is an info message(getAllDepartments)");
            return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(service.getAll());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Department> create(@RequestBody Department dep){

        LOG.info("This is an info message(createDepartment)");
        if (dep.getName() == null || dep.getFloor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.saveObject(dep));
        } else{
            return ResponseEntity.status(HttpStatus.OK).body(service.saveObject(dep));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Department> update(@RequestBody Department newDep, @PathVariable Integer id){

        LOG.info("This is an info message(updateDepartment)");
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
            LOG.info("This is an info message(deleteDepartment)");
            service.deleteObject(id);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }
}
