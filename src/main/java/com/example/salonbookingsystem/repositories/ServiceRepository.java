package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.Services;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {

    List<Services> findAllByServiceName(ServiceEnum name);

    Services findByServiceNameAndClientFeature(ServiceEnum serviceName,String clientFeature);
}
