package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.repositories.GenderRepository;
import com.example.salonbookingsystem.services.impl.GenderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenderServiceImplTest {

    @Mock
    private GenderRepository genderRepository;

    private GenderServiceImpl genderService;

    @BeforeEach
    void setUp() {
        this.genderService = new GenderServiceImpl(this.genderRepository);
    }

    @Test
    void initializeGenders_WhenGendersNotInitialized_ShouldSaveGenders() {
        // Arrange
        when(genderRepository.count()).thenReturn(0L);

        // Act
        genderService.initializeGenders();

        // Assert
        verify(genderRepository, times(1)).saveAll(anyList());
    }

    @Test
    void initializeGenders_WhenGendersAlreadyInitialized_ShouldNotSaveGenders() {
        // Arrange
        when(genderRepository.count()).thenReturn(2L);

        // Act
        genderService.initializeGenders();

        // Assert
        verify(genderRepository, never()).saveAll(anyList());
    }

    @Test
    void areGendersInit_WhenGendersInitialized_ShouldReturnTrue() {
        // Arrange
        when(genderRepository.count()).thenReturn(2L);

        // Act
        boolean result = this.genderService.areGendersInit();

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void areGendersInit_WhenGendersNotInitialized_ShouldReturnFalse() {
        // Arrange
        when(genderRepository.count()).thenReturn(0L);

        // Act
        boolean result = this.genderService.areGendersInit();

        // Assert
        Assertions.assertFalse(result);
    }

}
