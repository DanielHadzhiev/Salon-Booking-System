package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.HomeController;
import com.example.salonbookingsystem.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomeControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private HomeController homeController;

    @BeforeEach
    public void setUp() {
        this.homeController = new HomeController(this.userService);
    }

    @Test
    public void testGetIndexWithPrincipal() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(userService.getCurrentName()).thenReturn("Test");

        // Act
        String viewName = homeController.getIndex(model, principal);

        // Assert
        Assertions.assertEquals("services-home", viewName);
        verify(model, times(1)).addAttribute(eq("name"), eq("Test"));
        verify(userService, times(1)).getCurrentName();
    }

    @Test
    public void testGetAboutUs() {
        // Act
        String viewName = homeController.getAboutUs();

        // Assert
        Assertions.assertEquals("about-us", viewName);
    }

    @Test
    public void testNameModelAttribute() {
        // Act
        String attributeName = homeController.nameInit();

        // Assert
        Assertions.assertEquals("Anonymous", attributeName);
    }

}
