package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.LoginDTO;
import com.example.salonbookingsystem.model.dto.RegisterDTO;
import com.example.salonbookingsystem.model.entity.Gender;
import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.GenderRepository;
import com.example.salonbookingsystem.repositories.RoleRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@Transactional
@DirtiesContext
public class UserServiceImplIntTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Test
    void loginTest(){

        this.userRepository.save(createUser());

        Assertions.assertTrue(this.userService.loginUser(createLoginDTO()));

    }

    @Test
    void registerTest() throws IOException {

this.userService.registerUser(createRegisterDTO());

Assertions.assertEquals(1,this.userRepository.count());
    }

    private void initDb(){

        this.genderRepository.saveAll(createGender());
        this.roleRepository.saveAll(createRoles());

    }
    private RegisterDTO createRegisterDTO(){

        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setName("Test");
        registerDTO.setEmail("test@gmail.com");
        registerDTO.setPassword("test");
        registerDTO.setGender("MALE");
        registerDTO.setUserImage(new MockMultipartFile("file",
                "test.txt",
                "text/plain",
                "Hello, World!".getBytes()));

        return registerDTO;
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

    private LoginDTO createLoginDTO(){

        LoginDTO loginDTO = new LoginDTO();

        loginDTO.setEmail("test@gmail.com");
        loginDTO.setPassword("test");

        return loginDTO;
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
