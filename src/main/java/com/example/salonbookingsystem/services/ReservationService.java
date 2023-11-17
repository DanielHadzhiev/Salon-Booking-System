package com.example.salonbookingsystem.services;

import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.model.dto.UserReservationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservationService {

    void makeReservation(ReservationDTO reservationDTO);

    List<UserReservationDTO> visualizeReservations();

    void undoReservation(long id);
}
