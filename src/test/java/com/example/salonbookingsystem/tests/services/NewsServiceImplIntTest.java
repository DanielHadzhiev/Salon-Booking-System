package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import com.example.salonbookingsystem.model.entity.Gender;
import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.Services;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.*;
import com.example.salonbookingsystem.services.impl.NewsServiceImpl;
import com.example.salonbookingsystem.services.impl.SbUserDetailsService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@DirtiesContext
public class NewsServiceImplIntTest {

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private NewsServiceImpl newsService;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private SbUserDetailsService sbUserDetailsService;

    @BeforeEach
    void setUp(){

        this.roleRepository.deleteAll();
        this.userRepository.deleteAll();
        this.genderRepository.deleteAll();
        this.serviceRepository.deleteAll();
        this.newsRepository.deleteAll();

        initDb();

    }

    @Test
    void postNews() {

        setAuthenticationWithCustomUserDetails();

        this.newsService.postNews(createImportNewsDTO());

        Assertions.assertTrue(this.newsRepository.count() > 0);

    }

    private void setAuthenticationWithCustomUserDetails() {
        UserEntity user = createUser();

        UserDetails userDetails = this.sbUserDetailsService.loadUserByUsername(user.getEmail());

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void initDb (){

        this.genderRepository.saveAll(createGender());
        this.roleRepository.saveAll(createRoles());
        this.serviceRepository.saveAll(createServices());
        this.userRepository.save(createUser());

    }

    private ImportNewsDTO createImportNewsDTO(){

       ImportNewsDTO importNewsDTO =  new ImportNewsDTO();
       importNewsDTO.setContent("This is test");

       return importNewsDTO;

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
