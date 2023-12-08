package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.entity.Gender;
import com.example.salonbookingsystem.model.enums.GenderEnum;
import com.example.salonbookingsystem.repositories.GenderRepository;
import com.example.salonbookingsystem.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Autowired
    public GenderServiceImpl(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    @Override
    public void initializeGenders() {

        if(!areGendersInit()){

            Gender male = new Gender(GenderEnum.MALE);
            Gender female = new Gender(GenderEnum.FEMALE);

            this.genderRepository.saveAll(List.of(male,female));
        }

    }
    private boolean areGendersInit(){
       return this.genderRepository.count()>0;
    }
}
