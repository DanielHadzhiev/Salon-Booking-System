package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.model.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

Optional<List<UserEntity>> findAllByEmailNotAndRolesName(String email, RolesEnum roleName);

    Optional<UserEntity> findByEmail(String email);
}
