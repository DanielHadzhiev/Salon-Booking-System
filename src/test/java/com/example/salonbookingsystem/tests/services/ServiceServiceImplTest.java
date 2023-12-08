package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.model.dto.ServicesDTO;
import com.example.salonbookingsystem.model.entity.Services;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.ServiceRepository;
import com.example.salonbookingsystem.services.ServiceService;
import com.example.salonbookingsystem.services.impl.ServiceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceImplTest {

    private ServiceService service;
    @Mock
    private final ModelMapper modelMapper;

    @Mock
    private ServiceRepository repository;

    public ServiceServiceImplTest() {
        this.modelMapper = new ModelMapper();
    }

    @BeforeEach
    void setUp(){
        this.service = new ServiceServiceImpl(this.repository,this.modelMapper);
    }

    @Test
    void initializeServices_shouldSaveServicesIfNotInitialized() {
        // Arrange
        when(this.repository.count()).thenReturn(0L);

        // Act
        this.service.initializeServices();

        // Assert
        verify(this.repository, times(1)).saveAll(anyList());
    }

    @Test
    void initializeServices_shouldNotSaveServicesIfAlreadyInitialized() {
        // Arrange
        when(this.repository.count()).thenReturn(1L);

        // Act
        this.service.initializeServices();

        // Assert
        verify(this.repository, never()).saveAll(anyList());
    }


    @Test
    void getAllServicesTest(){

        // Arrange
        Services service = new Services(ServiceEnum.Trimming, 12, "men");
        ServicesDTO serviceDTO = new ServicesDTO();
        when(this.repository.findAll()).thenReturn(List.of(service));
        when(this.modelMapper.map(service, ServicesDTO.class)).thenReturn(serviceDTO);

        // Act
        List<ServicesDTO> result = this.service.getAllServices();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(serviceDTO, result.get(0));
    }

}
