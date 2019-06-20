package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Client.RestClient;
import com.example.java.spring.eureka.demo.Model.Doctor;
import com.example.java.spring.eureka.demo.Model.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private RestClient client;

    @Autowired
    public PatientController(RestClient client) {
        this.client = client;
    }

    //private List<Patient> patients = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllPatients() {

        ModelAndView model = new ModelAndView("patientList");

        List<Patient> patients = client.getAllPatients();
        model.addObject("patients", patients);
        return model;

//        Patient patient = new Patient(1, "qazwsx", "xswzaq", 45, "qwertytyui");
////        patient.setDoctor(new Doctor(1, "Oleksandr", "Khyzhniak", 20, 25000));
//        patients.add(patient);
//        patients.add(new Patient(3, "Oleksii", "Filatov", 100, "tanki"));
//        model.addObject("patients", patients);
//
//        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOnePatient(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("patientDetail");

        Patient patient = client.getOnePatient(id).get();
        model.addObject("patient", patient);
        //model.addObject("doctor", patient.getDoctor());
        return model;

        /*for(Patient pat : patients) {
            if (id == pat.getId()) {
                model.addObject("patient", pat);
                model.addObject("doctor", pat.getDoctor());
            }
        }
        return model;*/
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createPatient(@ModelAttribute Patient patient) {
        client.createPatient(patient);
        return new ModelAndView("redirect:/patient");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updatePatient(@PathVariable Integer id, @ModelAttribute Patient newPatient) {
        client.updatePatient(newPatient, id);
        return new ModelAndView("redirect:/patient");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePatient(@PathVariable Integer id) {

        if (client.deletePatient(id).getBody())
            return new ModelAndView("redirect:/patient");
        return new ModelAndView("redirect:/patient");
    }
}
