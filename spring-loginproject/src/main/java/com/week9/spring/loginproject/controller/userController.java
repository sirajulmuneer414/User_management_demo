package com.week9.spring.loginproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class userController {

    @GetMapping("/user/userDashboard")
    public String getHome(Model model, Principal principal){
        model.addAttribute("principalName",principal.getName());

        return "user/userDashboard";

    }
}
