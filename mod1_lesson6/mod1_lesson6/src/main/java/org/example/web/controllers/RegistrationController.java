package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    private final Logger logger = Logger.getLogger(RegistrationController .class);
    private final LoginService loginService;

    @Autowired
    public RegistrationController (LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("registrationForm", new LoginForm());
        return "registration_page";
    }

    @PostMapping("/join")
    public String join(LoginForm loginFrom) {
        loginService.join(loginFrom);
        logger.info("registration OK redirect to book shelf");
        return "redirect:/books/shelf";
    }
}

