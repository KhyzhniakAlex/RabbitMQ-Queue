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
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    private RestClient client;

    @Autowired
    public DepartmentController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllDepartments(Model model, String error, String logout) {

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<Department> departments = client.getAllDepartments().getBody();
            if (departments != null)
                model.addAttribute("departments", departments);
            return "departmentList";
            /*String departmentsStr = client.getAllDepartments().getBody().toString();
            Department[] departments;
            if (departmentsStr != null) {
                departments = DeserializeDepartmentList(departmentsStr);
                model.addAttribute("departments", departments);
            }
            return "departmentList";*/
        }
        return "";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDepartment(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("departmentDetail");

        Department department = client.getOneDepartment(id).getBody().get();
        model.addObject("department", department);
        if (department.getDoctors() != null)
            model.addObject("doctors", department.getDoctors());
        return model;
        /*String depStr = client.getOneDepartment(id).getBody().get().toString();
        Department dep;
        if (depStr != null) {
            dep = Deserialize(depStr);
            model.addObject("department", dep);
            if (dep.getDoctors() != null)
                model.addObject("doctors", dep.getDoctors());
        }*/
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createDepartment(@ModelAttribute Department department) {

        client.createDepartment(department);
        return new ModelAndView("redirect:/departments");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateDepartment(@PathVariable Integer id, @ModelAttribute Department newDepartment) {

        client.updateDepartment(id, newDepartment);
        return new ModelAndView("redirect:/departments");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDepartment(@PathVariable Integer id) {

        if (client.deleteDepartment(id).getBody())
            return new ModelAndView("redirect:/departments");
        return new ModelAndView("redirect:/departments");
    }







    /*private Department[] DeserializeDepartmentList(String departmentString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(departmentString, Department[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
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

    private Department Deserialize(String departmentString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(departmentString, Department.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }*/
}
