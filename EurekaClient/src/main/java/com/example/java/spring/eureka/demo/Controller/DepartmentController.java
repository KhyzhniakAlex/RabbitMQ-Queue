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

    //private List<Department> departments = new ArrayList<Department>();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDepartments() {

        ModelAndView model = new ModelAndView("departmentList");

        List<Department> departments = client.getAllDepartments();
        model.addObject("departments", departments);
        return model;

//        Department department = new Department(1, "abs", 2);
//        Doctor doc1 = new Doctor(1, "Oleksandr", "Khyzhniak", 20, 25000);
//        Doctor doc2 = new Doctor(2, "Bohdan", "Konorin", 21, 15000);
//        Set<Doctor> set = new HashSet<>();
//        set.add(doc1);
//        set.add(doc2);
//        department.setDoctors(set);
//        departments.add(department);
//        departments.add(new Department(2, "qwerty", 5));
//        model.addObject("departments", departments);

//        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDepartment(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("departmentDetail");

        Department department = client.getOneDepartment(id).get();
        model.addObject("department", department);
        //model.addObject("doctors", department.getDoctors());
        return model;

        /*for(Department dep : departments) {
            if (id == dep.getId()) {
                model.addObject("department", dep);
                //model.addObject("doctors", dep.getDoctors());
            }
        }
        return model;*/
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
