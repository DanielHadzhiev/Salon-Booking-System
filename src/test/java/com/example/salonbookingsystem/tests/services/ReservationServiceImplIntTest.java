package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.ReservationDTO;
import com.example.salonbookingsystem.model.entity.*;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.*;
import com.example.salonbookingsystem.services.ReservationService;
import com.example.salonbookingsystem.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@DirtiesContext
public class ReservationServiceImplIntTest {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationService reservationService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp(){

        this.roleRepository.deleteAll();
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();
        this.serviceRepository.deleteAll();

        initDb();

    }


   @Test
   void undoReservationTest(){

        this.reservationRepository.save(createReservation());

        this.reservationService.undoReservation(1);

        Assertions.assertEquals(0,this.reservationRepository.count());

   }



    private void initDb (){

        this.genderRepository.saveAll(createGender());
        this.roleRepository.saveAll(createRoles());
        this.serviceRepository.saveAll(createServices());
        this.userRepository.save(createUser());

    }

    private Reservation createReservation(){

        Reservation reservation = new Reservation();

        reservation.setId(1);
        reservation.setService(this.serviceRepository
                .findByServiceNameAndClientFeature(ServiceEnum.Trimming,"men"));
        reservation.setUser(this.userRepository.findByEmail("test@gmail.com").get());
        reservation.setComment("test");
        reservation.setAdditionalWashing(true);
        reservation.setDateAndHour(LocalDateTime.now());

        return reservation;

    }

    private static ReservationDTO createReservationDTO(){

        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setComment("This is testDTO.");
        reservationDTO.setAdditionalWashing(true);
        reservationDTO.setDateAndHour("2023-12-06T09:00");
        reservationDTO.setSelectedService("18");

        return reservationDTO;
    }

    private List<Services> createServices(){

        Services service1 = new Services(ServiceEnum.Trimming,10,"men");
        service1.setId(1);
        Services service2 = new Services(ServiceEnum.Dyeing,15,"women");
        service2.setId(2);


        return List.of(service1,service2);
    }

    private UserEntity createUser(){

        UserEntity userEntity = new UserEntity();

        userEntity.setName("Test");
        userEntity.setId(1);
        userEntity.setGender(this.genderRepository.findByGender(GenderEnum.MALE));
        userEntity.setPassword(this.passwordEncoder.encode("test"));
        userEntity.setRoles(List.of(this.roleRepository.findByName(RolesEnum.USER)));
        userEntity.setUserPhoto(new byte[]{1,2,3});
        userEntity.setEmail("test@gmail.com");

        return userEntity;

    }

    private static List<Gender> createGender(){

        return List.of(new Gender(GenderEnum.MALE),
                new Gender(GenderEnum.FEMALE));

    }

    private static List<Role> createRoles(){

        return List.of(new Role(RolesEnum.USER),
                new Role(RolesEnum.ADMIN));

    }
}
