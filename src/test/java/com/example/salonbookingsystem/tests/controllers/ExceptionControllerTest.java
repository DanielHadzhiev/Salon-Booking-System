package com.example.salonbookingsystem.tests.controllers;

import com.example.salonbookingsystem.controllers.ExceptionController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExceptionControllerTest {

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    void testHandleNotFound() {
        assertEquals("404", this.exceptionController.handleNotFound());
    }

    @Test
    void testHandleForbidden() {
        assertEquals("403", this.exceptionController.handleForbidden());
    }

    @Test
    void testHandleDefaultException() {
        assertEquals("error", this.exceptionController.handleDefaultException());
    }

}
