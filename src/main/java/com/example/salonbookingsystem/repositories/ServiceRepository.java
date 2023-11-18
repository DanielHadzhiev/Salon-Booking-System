package com.example.salonbookingsystem.repositories;

import com.example.salonbookingsystem.model.entity.Services;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {


    Services findByServiceNameAndClientFeature(ServiceEnum serviceName,String clientFeature);
}
