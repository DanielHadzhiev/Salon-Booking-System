package com.example.salonbookingsystem.tests.services;

import com.example.salonbookingsystem.repositories.RoleRepository;
import com.example.salonbookingsystem.services.RoleService;
import com.example.salonbookingsystem.services.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
     void setUp(){
     this.roleService = new RoleServiceImpl(this.roleRepository);
    }

    @Test
    void initializeRoles_shouldSaveRolesIfNotInitialized(){

        when(this.roleRepository.count()).thenReturn(0L);

        this.roleService.initializeRoles();

        verify(this.roleRepository,times(1)).saveAll(any());

    }
    @Test
    void initializeRoles_shouldNotSaveRolesIfInitialized(){

        when(this.roleRepository.count()).thenReturn(1L);

        this.roleService.initializeRoles();

        verify(this.roleRepository,times(0)).saveAll(any());

    }

}
