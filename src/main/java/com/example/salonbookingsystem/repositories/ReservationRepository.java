package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Optional<List<Reservation>> findAllByUserEmail(String email);
}
