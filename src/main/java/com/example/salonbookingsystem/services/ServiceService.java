package com.example.salonbookingsystem.services;

import com.example.salonbookingsystem.model.dto.ServicesDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceService {

    void initializeServices();

    List<ServicesDTO> getAllServices();


}
