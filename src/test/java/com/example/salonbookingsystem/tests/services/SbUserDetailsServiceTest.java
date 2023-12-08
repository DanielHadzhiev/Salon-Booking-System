package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.impl.SbUserDetailsService;
import com.example.salonbookingsystem.utils.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SbUserDetailsServiceTest {

    private SbUserDetailsService sbUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        this.sbUserDetailsService = new SbUserDetailsService(this.userRepository);
    }

    @Test
    void loadUserTest(){

        String email = "test@gmail.com";
        when(this.userRepository.findByEmail(email)).thenReturn(Optional.of(createUser()));

        CustomUserDetails userDetails = (CustomUserDetails)
                this.sbUserDetailsService.loadUserByUsername(email);

        Assertions.assertEquals("Test", userDetails.getUsername());
        Assertions.assertEquals("test@gmail.com", userDetails.getEmail());
        Assertions.assertEquals("test",userDetails.getPassword());
        Assertions.assertTrue(userDetails.getAuthorities().contains(createGrantedAuthority()));


    }

    private static UserEntity createUser(){
        UserEntity userEntity = new UserEntity();
        Role role = new Role(RolesEnum.USER);

        userEntity.setName("Test");
        userEntity.setEmail("test@gmail.com");
        userEntity.setPassword("test");
        userEntity.setRoles(List.of(role));

        return userEntity;
    }

    private static GrantedAuthority createGrantedAuthority(){
        return () -> "ROLE_USER";
    }
}
