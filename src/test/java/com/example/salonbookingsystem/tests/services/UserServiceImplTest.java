package com.example.salonbookingsystem.tests.services;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;

import com.example.salonbookingsystem.model.dto.*;
import com.example.salonbookingsystem.model.entity.*;
import com.example.salonbookingsystem.model.enums.*;
import com.example.salonbookingsystem.repositories.*;
import com.example.salonbookingsystem.services.impl.UserServiceImpl;
import com.example.salonbookingsystem.utils.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.*;

@ExtendWith(MockitoExtension.class)
    public class UserServiceImplTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private GenderRepository genderRepository;

        @Mock
        private ModelMapper modelMapper;

        @Mock
        private RoleRepository roleRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        private UserServiceImpl userService;

        public UserServiceImplTest() {
            this.modelMapper = new ModelMapper();

        }

        @BeforeEach
        void setUp() {
            this.userService = new UserServiceImpl(this.userRepository,
                    this.modelMapper,
                    this.genderRepository,
                    this.roleRepository,
                    this.passwordEncoder);
        }
    @Test
    void testGetCurrentEmail() {
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(mock(org.springframework.security.core.context.SecurityContext.class));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal())
                .thenReturn(createCustomUserDetails());

        Assertions.assertEquals("test@gmail.com", this.userService.getCurrentEmail());
    }

    @Test
    void testMakeAdmin(){

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setRoles(Collections.singletonList(new Role(RolesEnum.USER))); // Initial role

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByName(RolesEnum.ADMIN)).thenReturn(new Role(RolesEnum.ADMIN));

        userService.makeAdmin(1L);

        verify(userRepository).save(user);

        Assertions.assertEquals(1, user.getRoles().size());
        Assertions.assertEquals(RolesEnum.ADMIN, user.getRoles().get(0).getRole());

    }

        @Test
        void testGetName(){

            when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(createUser()));

            String currentName = this.userService.getCurrentName();

            Assertions.assertEquals(currentName,createUser().getName());
        }

        @Test
        void testLoginUser() {

            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail("test@gmail.com");
            loginDTO.setPassword("test");

            when(this.userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(createUser()));
            when(this.passwordEncoder.matches("test", createUser().getPassword())).thenReturn(true);


            boolean result = userService.loginUser(loginDTO);


            Assertions.assertTrue(result);
        }

        @Test
        void testExportProfile() {

            when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(createUser()));
            when(this.modelMapper.map(any(UserEntity.class), eq(ProfileDTO.class))).thenReturn(createProfileDTO());

            ProfileDTO result = this.userService.exportProfile();

            assertNotNull(result);
            Assertions.assertEquals("Test",result.getName());
            Assertions.assertEquals("test@gmail.com", result.getEmail());
            Assertions.assertEquals("MALE", result.getGender());
            Assertions.assertEquals("AQIDBA==", result.getUserImage());
        }

        @Test
        void testRegister() throws IOException {

            RegisterDTO registerDTO = createRegisterDTO();

            UserEntity expectedUser = createUser();

            when(modelMapper.map(registerDTO, UserEntity.class)).thenReturn(expectedUser);
            when(genderRepository.findByGender(any())).thenReturn(createGender());
            when(roleRepository.findByName(any())).thenReturn(createRole());
            when(passwordEncoder.encode(any())).thenReturn("test");

            this.userService.registerUser(registerDTO);

            verify(userRepository, times(1)).save(expectedUser);
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
        private static ProfileDTO createProfileDTO(){

            ProfileDTO mockProfileDTO = new ProfileDTO();
            mockProfileDTO.setName("Test");
            mockProfileDTO.setEmail("test@gmail.com");
            mockProfileDTO.setGender("MALE");
            mockProfileDTO.setUserImage("test");

            return mockProfileDTO;

        }

        private static CustomUserDetails createCustomUserDetails(){
            return new CustomUserDetails("test@gmail.com",
                    "test",
                    Collections.emptyList(),
                    "test@gmail.com");
        }

        private static RegisterDTO createRegisterDTO(){

            RegisterDTO registerDTO = new RegisterDTO();

            registerDTO.setName("Test");
            registerDTO.setEmail("test@gmail");
            registerDTO.setPassword("test");
            registerDTO.setGender("MALE");
            registerDTO.setUserImage(new MockMultipartFile("file",
                    "test.txt",
                    "text/plain",
                    "Hello, World!".getBytes()));

            return registerDTO;

        }

        private static Gender createGender(){

            return new Gender(GenderEnum.MALE);

        }

        private static Role createRole(){

            return new Role(RolesEnum.USER);

        }

    }

