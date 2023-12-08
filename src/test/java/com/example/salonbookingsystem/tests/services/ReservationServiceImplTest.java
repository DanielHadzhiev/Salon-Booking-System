package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.model.dto.UserReservationDTO;
import com.example.salonbookingsystem.model.entity.*;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.ReservationRepository;
import com.example.salonbookingsystem.repositories.ServiceRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.ReservationService;
import com.example.salonbookingsystem.services.impl.ReservationServiceImpl;
import com.example.salonbookingsystem.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserServiceImpl userService;

    private ReservationService reservationService;

    @BeforeEach
    void setUp(){

        this.reservationService = new ReservationServiceImpl(this.reservationRepository,
                this.serviceRepository,
                this.userRepository,
                this.modelMapper,
                this.userService);

    }

    @Test
    void makeReservationTest(){

        when(this.serviceRepository.findById(any())).thenReturn(Optional.of(createService()));
        when(this.userRepository.findByEmail(any())).thenReturn(Optional.of(createUser()));

        this.reservationService.makeReservation(createReservationDTO());

        verify(this.reservationRepository, times(1)).save(any());

    }

    @Test
    void testVisualizeReservationsWithEmptyReservations() {
        // Arrange
        when(this.userService.getCurrentEmail()).thenReturn("user@example.com");
        when(this.reservationRepository.findAllByUserEmail("user@example.com")).thenReturn(Optional.empty());

        // Act
        List<UserReservationDTO> result = this.reservationService.visualizeReservations();

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testUndoReservation() {

        long reservationId = 1L;
        Reservation reservation = new Reservation();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        reservationService.undoReservation(reservationId);

        verify(reservationRepository, times(1)).delete(reservation);
    }


    private  static ReservationDTO createReservationDTO(){

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setComment("This is testDTO.");
        reservationDTO.setAdditionalWashing(true);
        reservationDTO.setDateAndHour("2023-12-06T09:00");
        reservationDTO.setSelectedService("1");

        return reservationDTO;
    }

    private static Services createService(){

        return new Services(ServiceEnum.Trimming,
                10,
                "short on top");
    }

    private static UserEntity createUser() {

        UserEntity user = new UserEntity();
        Role role = new Role(RolesEnum.USER);
        Gender gender = new Gender(GenderEnum.MALE);

        user.setId(1);
        user.setName("Test");
        user.setPassword("test");
        user.setEmail("test@gmail");
        user.setRoles(List.of(role));
        user.setGender(gender);
        user.setReservations(new ArrayList<>());
        user.setUserPhoto(new byte[]{1, 2, 3, 4});

        return user;

    }
}
