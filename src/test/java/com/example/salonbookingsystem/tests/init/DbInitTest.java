package com.example.salonbookingsystem.tests.init;

import com.example.salonbookingsystem.init.DbInit;
import com.example.salonbookingsystem.services.GenderService;
import com.example.salonbookingsystem.services.RoleService;
import com.example.salonbookingsystem.services.ServiceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DbInitTest {

    @Mock
    private GenderService genderService;

    @Mock
    private RoleService roleService;

    @Mock
    private ServiceService serviceService;

    private DbInit dbInit;

    @BeforeEach
    void setUp(){
        this.dbInit = new DbInit(this.genderService,
                this.roleService,
                this.serviceService);
    }

    @Test
    void initDBShouldInitializeGendersServicesAndRoles() {
        // Arrange

        DbInit dbInit = new DbInit(this.genderService,
                this.roleService,
                this.serviceService);

        // Act
        dbInit.initDB();

        // Assert
        verify(this.genderService, times(1)).initializeGenders();
        verify(this.serviceService, times(1)).initializeServices();
        verify(this.roleService, times(1)).initializeRoles();
    }

}
