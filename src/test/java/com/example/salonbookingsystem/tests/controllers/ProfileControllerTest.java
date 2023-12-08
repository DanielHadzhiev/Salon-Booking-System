package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.ProfileController;
import com.example.salonbookingsystem.model.dto.ChangeEmailDTO;
import com.example.salonbookingsystem.model.dto.ChangeNameDTO;
import com.example.salonbookingsystem.model.dto.ChangePasswordDTO;
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
public class ProfileControllerTest {

    private ProfileController profileController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp(){
        this.profileController = new ProfileController(this.userService);
    }

    @Test
    public void testGetProfile() {
        String view = profileController.getProfile(model);

        Assertions.assertEquals("profile", view);
        verify(model).addAttribute("profileDTO", userService.exportProfile());
    }

    @Test
    public void testGetChangePassword() {
        String view = profileController.getChangePassword();

        Assertions.assertEquals("change-password", view);
    }

    @Test
    public void testGetChangeName() {
        String view = profileController.getChangeName();

        Assertions.assertEquals("change-name", view);
    }

    @Test
    public void testGetChangeEmail() {
        String view = profileController.getChangeEmail();

        Assertions.assertEquals("change-email", view);
    }

    @Test
    public void testPostChangePasswordSuccess() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        when(userService.changePassword(changePasswordDTO)).thenReturn(true);

        String view = profileController.postChangePassword(changePasswordDTO, bindingResult, redirectAttributes);

        Assertions.assertEquals("redirect:/profile", view);
        verify(userService,times(2)).changePassword(changePasswordDTO);
    }


    @Test
    public void testPostChangePasswordFailure() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        when(userService.changePassword(changePasswordDTO)).thenReturn(false);

        String view = profileController.postChangePassword(changePasswordDTO, bindingResult, redirectAttributes);

        Assertions.assertEquals("redirect:/profile/change-password", view);
        verify(redirectAttributes).addFlashAttribute("result", true);
    }

    @Test
    public void testPostChangeEmail() {
        ChangeEmailDTO changeEmailDTO = new ChangeEmailDTO();

        String view = profileController.postChangeEmail(changeEmailDTO, bindingResult, redirectAttributes);

        Assertions.assertEquals("redirect:/profile", view);
        verify(userService).changeEmail(changeEmailDTO);
    }

    @Test
    public void testPostChangeName() {
        ChangeNameDTO changeNameDTO = new ChangeNameDTO();

        String view = profileController.postChangeEmail(changeNameDTO, bindingResult, redirectAttributes);

        Assertions.assertEquals("redirect:/profile", view);
        verify(userService).changeName(changeNameDTO);
    }


}
