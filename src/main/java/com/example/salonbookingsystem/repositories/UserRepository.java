package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.User;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

Optional<List<User>> findAllByEmailNotAndRoleName(String email,RolesEnum roleName);

    Optional<User> findByEmail(String email);
}
