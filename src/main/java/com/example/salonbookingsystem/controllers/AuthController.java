package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.model.dto.LoginDTO;
import com.example.salonbookingsystem.model.dto.RegisterDTO;
import com.example.salonbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "registerDTO")
    public RegisterDTO registerDTOInit(){
        return new RegisterDTO();
    }

    @ModelAttribute(name = "loginDTO")
    public LoginDTO loginDTOInit(){
        return  new LoginDTO();
    }
    @ModelAttribute(name = "result")
    public boolean resultInit(){
        return false;
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/register")
    public String postRegister(@Valid RegisterDTO registerDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) throws IOException {

if(bindingResult.hasErrors()){
    redirectAttributes.addFlashAttribute("registerDTO",registerDTO);
    redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO",
            bindingResult);
    return "redirect:/register";
}
this.userService.registerUser(registerDTO);
return "redirect:/login";
    }

    @PostMapping("/login")
    public String postLogin(@Valid LoginDTO loginDTO,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){

        boolean result = this.userService.loginUser(loginDTO);

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginDTO",loginDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO",
                    bindingResult);
            return "redirect:/login";
        }
        if(!result){
            redirectAttributes.addFlashAttribute("result", true);
            return "redirect:/login";
        }
this.userService.loginUser(loginDTO);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String postLogOut(){

        return "redirect:/";
    }
}
