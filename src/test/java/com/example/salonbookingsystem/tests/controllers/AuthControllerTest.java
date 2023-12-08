package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.AuthController;
import com.example.salonbookingsystem.model.dto.LoginDTO;
import com.example.salonbookingsystem.model.dto.RegisterDTO;
import com.example.salonbookingsystem.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    private AuthController authController;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        this.authController = new AuthController(this.userService);
    }

    @Test
    void testGetRegister() {
        Assertions.assertEquals("register", authController.getRegister());
    }

    @Test
    void testGetLogin() {
        Assertions.assertEquals("login", authController.getLogin());
    }

    @Test
    void testPostRegisterWithValidInput() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        when(bindingResult.hasErrors()).thenReturn(false);

        Assertions.assertEquals("redirect:/login", authController.postRegister(registerDTO, bindingResult, redirectAttributes));

        verify(userService, times(1)).registerUser(registerDTO);
    }

    @Test
    void testPostRegisterWithInvalidInput() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        Assertions.assertEquals("redirect:/register", authController.postRegister(registerDTO, bindingResult, redirectAttributes));

        verify(redirectAttributes, times(1))
                .addFlashAttribute(eq("registerDTO"), any(RegisterDTO.class));
        verify(redirectAttributes, times(1))
                .addFlashAttribute(eq(BindingResult.MODEL_KEY_PREFIX + "registerDTO"), eq(bindingResult));
    }

    @Test
    void testPostLoginWithValidCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.loginUser(loginDTO)).thenReturn(true);

        Assertions.assertEquals("redirect:/", authController.postLogin(loginDTO, bindingResult, redirectAttributes));

        verify(userService, times(2)).loginUser(loginDTO);
    }

    @Test
    void testPostLoginWithInvalidCredentials() {
        LoginDTO loginDTO = new LoginDTO();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.loginUser(loginDTO)).thenReturn(false);

        Assertions.assertEquals("redirect:/login", authController.postLogin(loginDTO, bindingResult, redirectAttributes));

        verify(redirectAttributes, times(1)).addFlashAttribute("result", true);
    }

    @Test
    void testPostLoginWithInvalidInput() {
        LoginDTO loginDTO = new LoginDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        Assertions.assertEquals("redirect:/login", authController.postLogin(loginDTO, bindingResult, redirectAttributes));

        verify(redirectAttributes, times(1)).addFlashAttribute
                (eq("loginDTO"), any(LoginDTO.class));
        verify(redirectAttributes, times(1)).addFlashAttribute
                (eq(BindingResult.MODEL_KEY_PREFIX + "loginDTO"), eq(bindingResult));
    }

    @Test
    void testPostLogOut() {
        Assertions.assertEquals("redirect:/", authController.postLogOut());
    }
}
