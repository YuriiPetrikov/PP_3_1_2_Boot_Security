package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping( "/user")
    public String printUser(Authentication authentication, ModelMap model) {
        model.addAttribute( "user", authentication.getPrincipal());
        return "user";
    }
}
