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

    //private List<Doctor> doctors = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllDoctors() {

        ModelAndView model = new ModelAndView("doctorList");

        List<Doctor> doctors = client.getAllDoctors();
        model.addObject("doctors", doctors);
        return model;

//            Doctor doc1 = new Doctor(1, "Oleksandr", "Khyzhniak", 20, 25000);
////            Patient pat1 = new Patient(1, "qazwsx", "xswzaq", 45, "qwertytyui");
////            Patient pat2 = new Patient(2, "tgbyhn", "mjunhy", 45, "zxcvbnmm");
////            Set<Patient> set = new HashSet<>();
////            set.add(pat1);
////            set.add(pat2);
////            doc1.setPatients(set);
////            doc1.setDepartment(new Department(1, "abs", 2));
//            doctors.add(doc1);
//            doctors.add(new Doctor(3, "Oleksii", "Timchenko", 21, 15000));
//            model.addObject("doctors", doctors);
//
//            return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getOneDoctor(@PathVariable Integer id) {

        ModelAndView model = new ModelAndView("doctorDetail");

        Doctor doctor = client.getOneDoctor(id).get();
        model.addObject("doctor", doctor);
        //model.addObject("department",doctor.getDepartment());
        //model.addObject("patients",doctor.getPatients());
        return model;

        /*for(Doctor doc : doctors) {
            if (id == doc.getId()) {
                model.addObject("doctor", doc);
                //model.addObject("department",doc.getDepartment());
                //model.addObject("patients",doc.getPatients());
            }
        }
        return model;*/
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
