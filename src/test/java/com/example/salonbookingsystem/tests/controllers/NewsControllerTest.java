package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.NewsController;
import com.example.salonbookingsystem.model.dto.EmailsExportDTO;
import com.example.salonbookingsystem.model.dto.ImportNewsDTO;
import com.example.salonbookingsystem.model.dto.ExportNewsDTO;
import com.example.salonbookingsystem.services.NewsService;
import com.example.salonbookingsystem.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NewsControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private NewsService newsService;

    private NewsController newsController;

    @BeforeEach
    void setUp() {
        this.newsController = new NewsController(this.userService,
                this.newsService);
    }

    @Test
    void testGetNews() {
        Model model = mock(Model.class);

        when(newsService.visualizeWeather()).thenReturn(getMockWeatherMap());
        when(newsService.visualizeNews()).thenReturn(getMockNewsList());

        String viewName = newsController.getNews(model);

        assertEquals("news", viewName);
        verify(model, times(1)).addAttribute(eq("newsExportDTO"), any(List.class));
        verify(model, times(1)).addAttribute(eq("city"), eq("Sofia"));
        verify(model, times(1)).addAttribute(eq("weatherDescription"), eq("MockWeather"));
        verify(model, times(1)).addAttribute(eq("iconCode"), eq("MockIcon"));
        verify(model, times(1)).addAttribute(eq("temp"), eq("MockTemp"));
    }

    @Test
    void testGetMakeAdmin() {
        Model model = mock(Model.class);

        when(userService.getAllEmails()).thenReturn(getMockEmailsExportDTOList());

        String viewName = newsController.getMakeAdmin(model);

        assertEquals("make-admin", viewName);
        verify(model, times(1)).addAttribute(eq("emailsExportDTO"), any(List.class));
    }

    @Test
    void testPostPostNews() {
        ImportNewsDTO newsImportDTO = getMockImportNewsDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = newsController.postPostNews(newsImportDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/post-news", viewName);
        verify(newsService, times(1)).postNews(eq(newsImportDTO));


        when(bindingResult.hasErrors()).thenReturn(true);

        viewName = newsController.postPostNews(newsImportDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:/post-news", viewName);
        verify(redirectAttributes, times(1)).addFlashAttribute(eq("newsImportDTO"), eq(newsImportDTO));
        verify(redirectAttributes, times(1)).addFlashAttribute(
                eq("org.springframework.validation.BindingResult.newsImportDTO"), eq(bindingResult));
    }


    private Map<String, String> getMockWeatherMap() {

        return Map.of("weather", "MockWeather", "icon", "MockIcon", "temp", "MockTemp");
    }

    private List<ExportNewsDTO> getMockNewsList() {

        return new ArrayList<>();
    }

    private List<EmailsExportDTO> getMockEmailsExportDTOList() {

        return new ArrayList<>();
    }

    private ImportNewsDTO getMockImportNewsDTO() {

        return new ImportNewsDTO();
    }
}
