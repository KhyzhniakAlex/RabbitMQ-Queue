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

    @RequestMapping(method = RequestMethod.GET)
    public String getAllPatients(Model model, String error, String logout) {

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<Patient> patients = client.getAllPatients().getBody();
            if (patients != null)
                model.addAttribute("patients", patients);
            return "patientList";
            /*String patientsStr = client.getAllPatients().getBody().toString();
            Patient[] patients;
            if (patientsStr != null)
            {
                patients = DeserializePatientList(patientsStr);
                model.addAttribute("patients", patients);
            }
            return "patientList";*/
        }
        return "";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOnePatient(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("patientDetail");

        Patient patient = client.getOnePatient(id).getBody().get();
        model.addObject("patient", patient);
        if (patient.getDoctor() != null)
            model.addObject("doctor", patient.getDoctor());
        return model;
        /*String patStr = client.getOnePatient(id).getBody().get().toString();
        Patient pat;
        if (patStr != null) {
            pat = Deserialize(patStr);
            model.addObject("patient", pat);
            if (pat.getDoctor() != null)
                model.addObject("doctor", pat.getDoctor());
        }*/
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createPatient(@ModelAttribute Patient patient) {

        client.createPatient(patient);
        return new ModelAndView("redirect:/patients");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updatePatient(@PathVariable Integer id, @ModelAttribute Patient newPatient) {

        client.updatePatient(id, newPatient);
        return new ModelAndView("redirect:/patients");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deletePatient(@PathVariable Integer id) {

        if (client.deletePatient(id).getBody())
            return new ModelAndView("redirect:/patients");
        return new ModelAndView("redirect:/patients");
    }





    /*private Patient[] DeserializePatientList(String patientString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(patientString, Patient[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Doctor[] DeserializeDoctorList(String doctorString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(doctorString, Doctor[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Patient Deserialize(String objectString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(objectString, Patient.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
