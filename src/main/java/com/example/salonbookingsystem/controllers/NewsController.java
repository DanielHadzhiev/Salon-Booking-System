package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.model.dto.EmailsExportDTO;
import com.example.salonbookingsystem.model.dto.ExportNewsDTO;
import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import com.example.salonbookingsystem.services.NewsService;
import com.example.salonbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class NewsController {


    private final UserService userService;

    private final NewsService newsService;

    @Autowired
    public NewsController(UserService userService,
                          NewsService newsService) {
        this.userService = userService;
        this.newsService = newsService;
    }

    @ModelAttribute(name = "emailsExportDTO")
    public EmailsExportDTO emailsExportDTOInit(){
        return new EmailsExportDTO();
    }

    @ModelAttribute(name = "error")
    public boolean errorInit(){
        return false;
    }

    @ModelAttribute(name = "newsImportDTO")
    public ImportNewsDTO importNewsDTOInit(){
        return new ImportNewsDTO();
    }

    @ModelAttribute(name = "newsExportDTO")
    public List<ExportNewsDTO> newsExportDTOInit(){
        return new ArrayList<>();
    }

    @GetMapping("/news")
    public String getNews(Model model){

        Map<String, String> weatherMap = this.newsService.visualizeWeather();

        model.addAttribute("newsExportDTO",this.newsService.visualizeNews());
        model.addAttribute("city","Sofia");
        model.addAttribute("weatherDescription",weatherMap.get("weather"));
        model.addAttribute("iconCode",weatherMap.get("icon"));
        model.addAttribute("temp",weatherMap.get("temp"));

        return "news";
    }

    @GetMapping("/post-news")
    public String getPostNews(){
        return "post-news";
    }

    @GetMapping("/make-admin")
    public String getMakeAdmin(Model model){

        model.addAttribute("emailsExportDTO",this.userService.getAllEmails());

        return "make-admin";
    }
    @PostMapping("/make-admin")
    public String postMakeAdmin(@RequestParam(value = "selectedEmailId",required = false) String selectedEmailId,
                                RedirectAttributes redirectAttributes){

        if(selectedEmailId == null || selectedEmailId.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("error",true);
            return "redirect:/make-admin";
        }

        this.userService.makeAdmin(Long.parseLong(selectedEmailId));

        return "redirect:/make-admin";
    }
    @PostMapping("/post-news")
    public String postPostNews(@Valid ImportNewsDTO newsImportDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("newsImportDTO",newsImportDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.newsImportDTO",bindingResult);

            return "redirect:/post-news";
        }

        this.newsService.postNews(newsImportDTO);
        return "redirect:/post-news";

    }
}
