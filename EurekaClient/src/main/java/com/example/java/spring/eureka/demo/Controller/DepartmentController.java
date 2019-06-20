package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Department;
import com.example.java.spring.eureka.demo.Model.Doctor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private RestClient client;

    @Autowired
    public DepartmentController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDepartments() {

        ModelAndView model = new ModelAndView("departmentList");

        List<Department> departments = client.getAllDepartments();
        model.addObject("departments", departments);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDepartment(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("departmentDetail");

        Department department = client.getOneDepartment(id).get();
        model.addObject("department", department);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createDepartment(@ModelAttribute Department department) {
        client.createDepartment(department);
        return new ModelAndView("redirect:/department");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateDepartment(@PathVariable Integer id, @ModelAttribute Department newDepartment) {
        client.updateDepartment(newDepartment, id);
        return new ModelAndView("redirect:/department");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDepartment(@PathVariable Integer id) {

        if (client.deleteDepartment(id).getBody())
            return new ModelAndView("redirect:/department");
        return new ModelAndView("redirect:/department");
    }
}
