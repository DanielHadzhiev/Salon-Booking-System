package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.model.dto.ChangeEmailDTO;
import com.example.salonbookingsystem.model.dto.ChangeNameDTO;
import com.example.salonbookingsystem.model.dto.ChangePasswordDTO;
import com.example.salonbookingsystem.model.dto.ProfileDTO;
import com.example.salonbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute(name = "profileDTO")
    public ProfileDTO profileDTO(){
        return new ProfileDTO();
    }

    @ModelAttribute(name = "changePasswordDTO")
    public ChangePasswordDTO changePasswordDTOInit(){
        return new ChangePasswordDTO();
    }
    @ModelAttribute(name = "result")
    public boolean resultInit(){
        return false;
    }
    @ModelAttribute(name = "changeEmailDTO")
    public ChangeEmailDTO ChangeEmailDTOInit(){
        return new ChangeEmailDTO();
    }
    @ModelAttribute(name = "changeNameDTO")
    public ChangeNameDTO ChangeNameDTOInit(){
        return new ChangeNameDTO();
    }


    @GetMapping("/profile")
    public String getProfile(Model model){

        model.addAttribute("profileDTO",this.userService.exportProfile());

        return "profile";
    }
@GetMapping("/profile/change-password")
    public String getChangePassword(){
        return "change-password";
}
@GetMapping("/profile/change-name")
    public String getChangeName(){
        return "change-name";
}
@GetMapping("/profile/change-email")
    public String getChangeEmail(){
        return "change-email";
}

@PostMapping("/profile/change-password")
    public String postChangePassword(@Valid ChangePasswordDTO changePasswordDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes){

    boolean result = this.userService.changePassword(changePasswordDTO);

    if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("changePasswordDTO",changePasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDTO",
                    bindingResult);

            return "redirect:/profile/change-password";

        }
    if(!result){
        redirectAttributes.addFlashAttribute("result", true);
        return "redirect:/profile/change-password";
    }

this.userService.changePassword(changePasswordDTO);
        return "redirect:/profile";
}

@PostMapping("/profile/change-email")
    public String postChangeEmail(@Valid ChangeEmailDTO changeEmailDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("changeEmailDTO",changeEmailDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeEmailDTO",
                    bindingResult);

            return "redirect:/profile/change-email";

        }
        this.userService.changeEmail(changeEmailDTO);
        return "redirect:/profile";

    }
    @PostMapping("/profile/change-name")
    public String postChangeEmail(@Valid ChangeNameDTO changeNameDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("changeEmailDTO",changeNameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeNameDTO",
                    bindingResult);

            return "redirect:/profile/change-name";

        }
        this.userService.changeName(changeNameDTO);
        return "redirect:/profile";

    }

}
