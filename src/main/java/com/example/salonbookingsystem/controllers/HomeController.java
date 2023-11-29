package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("name")
    public String nameInit(){
        return "Anonymous";
    }

    @GetMapping("/")
    public String getIndex(Model model,
                           Principal principal){

        if(principal!=null){
            model.addAttribute("name",this.userService.getCurrentName());
            return "services-home";
        }
        return "index";
    }
    @GetMapping("/about-us")
    public String getAboutUs(){
        return "about-us";
    }
}
