package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.ExportNewsDTO;
import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import com.example.salonbookingsystem.model.entity.Gender;
import com.example.salonbookingsystem.model.entity.News;
import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.NewsRepository;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.UserService;
import com.example.salonbookingsystem.services.impl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private UserService userService;

    private NewsServiceImpl newsService;

    @BeforeEach
    void setUp(){

        this.newsService = new NewsServiceImpl(this.newsRepository,
                this.modelMapper,
                this.userRepository,
                this.restTemplate,
                this.userService);
    }

    @Test
    void postNews_shouldSaveNewsIfUserExists() {
        ImportNewsDTO importNewsDTO = new ImportNewsDTO();
        importNewsDTO.setContent("Test News");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(userEntity));
        when(modelMapper.map(any(), any())).thenReturn(new News());

        newsService.postNews(importNewsDTO);

        verify(newsRepository, times(1)).save(any());
    }

    @Test
    void postNews_shouldNotSaveNewsIfUserDoesNotExist() {
        ImportNewsDTO importNewsDTO = new ImportNewsDTO();
        importNewsDTO.setContent("Test News");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        newsService.postNews(importNewsDTO);

        verify(newsRepository, never()).save(any());
    }

    @Test
    void visualizeNews_shouldReturnListOfExportNewsDTO() {
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setPublisher(createUser());
        newsList.add(news);

        when(newsRepository.findAll()).thenReturn(newsList);
        when(modelMapper.map(any(), any())).thenReturn(new ExportNewsDTO());

        List<ExportNewsDTO> result = newsService.visualizeNews();

        assertEquals(1, result.size());
    }

    @Test
    void visualizeNews_shouldReturnEmptyListIfNoNews() {
        when(newsRepository.findAll()).thenReturn(new ArrayList<>());

        List<ExportNewsDTO> result = newsService.visualizeNews();

        assertEquals(0, result.size());
    }

    @Test
    void resetNews_shouldDeleteAllNews() {
        newsService.resetNews();

        verify(newsRepository, times(1)).deleteAll();
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
