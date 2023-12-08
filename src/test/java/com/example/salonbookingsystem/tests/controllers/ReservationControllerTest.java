package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.ReservationController;
import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.services.ReservationService;
import com.example.salonbookingsystem.services.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    @Mock
    private ServiceService serviceService;

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp(){
        this.reservationController = new ReservationController(this.serviceService,
                this.reservationService);
    }

    @Test
    void testGetMyReservations() {
        when(reservationService.visualizeReservations()).thenReturn(Collections.emptyList());

        String result = reservationController.getMyReservations(model);

        assertEquals("my-reservations", result);
        verify(model).addAttribute(eq("userReservationsDTO"), any());
    }

    @Test
    void testGetMakeReservation() {
        when(serviceService.getAllServices()).thenReturn(Collections.emptyList());

        String result = reservationController.getMakeReservation(model);

        assertEquals("reservation", result);
        verify(model).addAttribute(eq("servicesDTO"), any());
    }

    @Test
    void testPostReservationWithErrors() {
        ReservationDTO reservationDTO = new ReservationDTO();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = reservationController.postReservation(reservationDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/make-reservation", result);
        verify(redirectAttributes).addFlashAttribute(eq("reservationDTO"), eq(reservationDTO));
        verify(redirectAttributes).addFlashAttribute(eq("org.springframework.validation.BindingResult.reservationDTO"), eq(bindingResult));
        verify(reservationService, never()).makeReservation(any(ReservationDTO.class));
    }

    @Test
    void testPostReservationWithoutErrors() {
        ReservationDTO reservationDTO = new ReservationDTO();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = reservationController.postReservation(reservationDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/make-reservation", result);
        verify(redirectAttributes, never()).addFlashAttribute(eq("reservationDTO"), eq(reservationDTO));
        verify(redirectAttributes, never()).addFlashAttribute(eq("org.springframework.validation.BindingResult.reservationDTO"), eq(bindingResult));
        verify(reservationService).makeReservation(eq(reservationDTO));
    }

    @Test
    void testGetUndoReservation() {
        long reservationId = 1L;

        String result = reservationController.getUndoReservation(reservationId);

        assertEquals("redirect:/my-reservations", result);
        verify(reservationService).undoReservation(eq(reservationId));
    }

}
