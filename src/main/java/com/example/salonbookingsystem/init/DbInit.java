package com.example.salonbookingsystem.init;

import com.example.salonbookingsystem.services.GenderService;
import com.example.salonbookingsystem.services.RoleService;
import com.example.salonbookingsystem.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbInit implements CommandLineRunner {

    private final GenderService genderService;

    private final RoleService roleService;

    private final ServiceService serviceService;

@Autowired
    public DbInit(GenderService genderService, RoleService roleService, ServiceService serviceService) {
        this.genderService = genderService;
        this.roleService = roleService;
        this.serviceService = serviceService;
    }

    @Override
    public void run(String... args){
initDB();

    }
    public void initDB(){

    this.genderService.initializeGenders();
    this.serviceService.initializeServices();
    this.roleService.initializeRoles();
    }
}
