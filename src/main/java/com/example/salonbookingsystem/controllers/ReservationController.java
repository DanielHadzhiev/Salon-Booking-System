package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.model.entity.Services;
import com.example.salonbookingsystem.services.ReservationService;
import com.example.salonbookingsystem.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    private final ServiceService service;

    private final ReservationService reservationService;


    @Autowired
    public ReservationController( ServiceService serviceService,
                                  ReservationService reservationService) {
        this.service = serviceService;
        this.reservationService = reservationService;
    }

    @ModelAttribute(name = "servicesDTO")
    public List<Services> servicesDTOInit(){
        return new ArrayList<>();
    }

    @ModelAttribute(name = "reservationDTO")
    public ReservationDTO reservationDTOInit(){
        return new ReservationDTO();
    }

    @GetMapping("/my-reservations")
    public String getMyReservations(Model model){

        model.addAttribute("userReservationsDTO",this.reservationService.visualizeReservations());

        return "my-reservations";
    }

    @GetMapping("/make-reservation")
    public String getMakeReservation(Model model){

        model.addAttribute("servicesDTO",this.service.getAllServices());
        return "reservation";
    }

    @PostMapping("/make-reservation")
    public String postReservation(@Valid ReservationDTO reservationDTO,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            redirectAttributes.addFlashAttribute("reservationDTO",reservationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.reservationDTO",
                    bindingResult);

            return "redirect:/make-reservation";

        }
        this.reservationService.makeReservation(reservationDTO);

        return "redirect:/make-reservation";

    }
    @GetMapping("/my-reservations/undo-reservation/{reservationId}")
    public String getUndoReservation(@PathVariable long reservationId){

        this.reservationService.undoReservation(reservationId);

        return "redirect:/my-reservations";

    }
}
