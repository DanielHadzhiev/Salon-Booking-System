package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.RoleRepository;
import com.example.salonbookingsystem.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initializeRoles() {

        if(!areRolesInit()){

            Role admin = new Role(RolesEnum.ADMIN);
            Role user = new Role(RolesEnum.USER);

            this.roleRepository.saveAll(List.of(admin,user));

        }

    }

    private boolean areRolesInit(){
        return this.roleRepository.count()>0;
    }
}
