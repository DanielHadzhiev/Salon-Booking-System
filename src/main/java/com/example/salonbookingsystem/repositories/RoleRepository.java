package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(RolesEnum name);
}
