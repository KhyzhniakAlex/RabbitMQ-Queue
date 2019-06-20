package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Department;
import com.example.java.spring.eureka.demo.Model.Doctor;
import com.example.java.spring.eureka.demo.Model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/doctor")
public class DoctorController {

    private RestClient client;

    @Autowired
    public DoctorController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDoctors() {

        ModelAndView model = new ModelAndView("doctorList");

        List<Doctor> doctors = client.getAllDoctors();
        model.addObject("doctors", doctors);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDoctor(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("doctorDetail");

        Doctor doctor = client.getOneDoctor(id).get();
        model.addObject("doctor", doctor);
        return model;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createDoctor(@ModelAttribute Doctor doctor) {
        client.createDoctor(doctor);
        return new ModelAndView("redirect:/doctor");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateDoctor(@PathVariable Integer id, @ModelAttribute Doctor newDoctor) {
        client.updateDoctor(newDoctor, id);
        return new ModelAndView("redirect:/doctor");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDoctor(@PathVariable Integer id) {

        if (client.deleteDoctor(id).getBody())
            return new ModelAndView("redirect:/doctor");
        return new ModelAndView("redirect:/doctor");
    }
}
