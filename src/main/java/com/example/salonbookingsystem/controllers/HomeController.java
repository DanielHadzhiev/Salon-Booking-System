package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndex(){

        if(userService.isUserLogged()){
            return "services-home";
        }
        return "index";
    }
    @GetMapping("/about-us")
    public String getAboutUs(){
        return "about-us";
    }
}
