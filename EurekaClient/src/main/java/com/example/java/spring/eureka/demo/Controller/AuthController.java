package com.example.java.spring.eureka.demo.Controller;

import com.example.java.spring.eureka.demo.Model.Authorities;
import com.example.java.spring.eureka.demo.Model.Users;
import com.example.java.spring.eureka.demo.Repository.AuthRepository;
import com.example.java.spring.eureka.demo.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AuthController {

    @Autowired
    AuthRepository authRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username or password are invalid.");

        if (logout != null)
            model.addAttribute("msg", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String menu(Model model) {
        return "main";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("errorMsg", "Your username and password are invalid.");

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration_post(@ModelAttribute Users users, String role) {
        users.setEnabled(true);
        authRepository.save(users);
        Authorities a = new Authorities(users.getUsername(),"ROLE_"+role.toUpperCase());
        roleRepository.save(a);
        return "redirect:/";
    }
}
