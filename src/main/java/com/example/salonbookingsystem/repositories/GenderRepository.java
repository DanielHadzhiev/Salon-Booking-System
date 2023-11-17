package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.Gender;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Long> {

    Gender findByGender(GenderEnum gender);
}
