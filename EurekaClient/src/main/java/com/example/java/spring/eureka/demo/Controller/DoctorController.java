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

@RestController
public class DoctorController {

    private RestClient client;

    @Autowired
    public DoctorController(RestClient client) {
        this.client = client;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAllDoctors(Model model, String error, String logout) {

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            List<Doctor> doctors = client.getAllDoctors().getBody();
            if (doctors != null)
                model.addAttribute("doctors", doctors);
            return "doctorList";
            /*String doctorsStr = client.getAllDoctors().getBody().toString();
            Doctor[] doctors;
            if (doctorsStr != null) {
                doctors = DeserializeDoctorList(doctorsStr);
                model.addAttribute("doctors", doctors);
            }
            return "doctorList";*/
        }
        return "";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDoctor(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("doctorDetail");

        Doctor doctor = client.getOneDoctor(id).getBody().get();
        model.addObject("doctor", doctor);
        if (doctor.getDepartment() != null)
            model.addObject("department",doctor.getDepartment());
        if (doctor.getPatients() != null)
            model.addObject("patients",doctor.getPatients());
        return model;
        /*String docStr = client.getOneDoctor(id).getBody().get().toString();
        Doctor doc;
        if (docStr != null)
        {
            doc = Deserialize(docStr);
            model.addObject("doctor", doc);
            if (doc.getPatients() != null)
                model.addObject("patients", doc.getPatients());
            if (doc.getDepartment() != null)
                model.addObject("department", doc.getDepartment());
        }*/
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createDoctor(@ModelAttribute Doctor doctor) {
        client.createDoctor(doctor);
        return new ModelAndView("redirect:/doctors");
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ModelAndView updateDoctor(@PathVariable Integer id, @ModelAttribute Doctor newDoctor) {
        client.updateDoctor(id, newDoctor);
        return new ModelAndView("redirect:/doctors");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteDoctor(@PathVariable Integer id) {

        if (client.deleteDoctor(id).getBody())
            return new ModelAndView("redirect:/doctors");
        return new ModelAndView("redirect:/doctors");
    }




    /*private Doctor[] DeserializeDoctorList(String doctorString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(doctorString, Doctor[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private Patient[] DeserializePatientList(String patientString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(patientString, Patient[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private Doctor Deserialize(String objectString)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(objectString, Doctor.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }*/
}
