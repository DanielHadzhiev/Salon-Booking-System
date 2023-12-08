package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.model.dto.UserReservationDTO;
import com.example.salonbookingsystem.model.entity.Reservation;
import com.example.salonbookingsystem.repositories.ReservationRepository;
import com.example.salonbookingsystem.repositories.ServiceRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.ReservationService;
import com.example.salonbookingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final ServiceRepository serviceRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final UserService userService;


    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  ServiceRepository serviceRepository,
                                  UserRepository userRepository,
                                  ModelMapper modelMapper,
                                  UserService userService) {
        this.reservationRepository = reservationRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void makeReservation(ReservationDTO reservationDTO) {

this.reservationRepository.save(this.mapDTO(reservationDTO));

    }

    @Override
    public List<UserReservationDTO> visualizeReservations() {

        Optional<List<Reservation>> allReservationsOptional =  this.reservationRepository
                .findAllByUserEmail(this.userService.getCurrentEmail());

        if(allReservationsOptional.isEmpty()){
            return new ArrayList<>();
        }

        List<Reservation> allReservations = allReservationsOptional.get();

        List<UserReservationDTO>reservationDTOS = new ArrayList<>();

        for (Reservation reservation:allReservations){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm",Locale.US);

            UserReservationDTO dto = this.modelMapper.map(reservation, UserReservationDTO.class);

            dto.setService(String.format("%s-%s",
                    reservation.getService().getServiceName(),
                    reservation.getService().getClientFeature()));
            dto.setDateAndHour(reservation.getDateAndHour().format(formatter));
            dto.setPrice(this.serviceRepository
                    .findByServiceNameAndClientFeature(reservation.getService().getServiceName(),
                            reservation.getService().getClientFeature()).getPrice());
            if(reservation.isAdditionalWashing()){
                dto.setAdditionalWashing("Yes");
            }
            else{
                dto.setAdditionalWashing("No");
            }


            reservationDTOS.add(dto);

        }
return reservationDTOS;
    }

    @Override
    public void undoReservation(long id) {

        Optional<Reservation> byId = this.reservationRepository.findById(id);

        byId.ifPresent(reservation -> this.reservationRepository.delete(reservation));

    }

    private Reservation mapDTO(ReservationDTO reservationDTO){

        Reservation reservation = new Reservation();

        reservation.setService(this.serviceRepository
                .findById(Long.parseLong(reservationDTO.getSelectedService())).get());

        reservation.setComment(reservationDTO.getComment());

        reservation.setAdditionalWashing(reservationDTO.getAdditionalWashing());

        reservation.setDateAndHour(convertDate(reservationDTO.getDateAndHour()));

        reservation.setUser(this.userRepository.findByEmail(this.userService.getCurrentEmail()).get());

        return reservation;

    }
    private LocalDateTime convertDate(String date){

        String pattern = "yyyy-MM-dd'T'HH:mm";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);

        return LocalDateTime.parse(date, formatter);

    }
}
