package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.dto.ServicesDTO;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.ServiceRepository;
import com.example.salonbookingsystem.services.ServiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import com.example.salonbookingsystem.model.entity.Services;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {

    private ServiceRepository serviceRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository,
                              ModelMapper modelMapper) {
        this.serviceRepository = serviceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initializeServices() {

        if(!areServicesInit()){
            Services trimmingMen = new Services(ServiceEnum.Trimming,12,"men");
            Services trimmingWomen = new Services(ServiceEnum.Trimming,10,"women");
            Services pensionersMen = new Services(ServiceEnum.Pensioners,8,"men");
            Services pensionersWomen = new Services(ServiceEnum.Pensioners,10,"women");
            Services curlsShort = new Services(ServiceEnum.Curls,25,"short hair");
            Services curlsNormal = new Services(ServiceEnum.Curls,35,"normal hair");
            Services curlsLong = new Services(ServiceEnum.Curls,50,"long hair");
            Services braidingWhole = new Services(ServiceEnum.Braiding,30,"whole hair");
            Services braidingHalf = new Services(ServiceEnum.Braiding,20,"half hair");
            Services dyeingShort = new Services(ServiceEnum.Dyeing,15,"short hair");
            Services dyeingNormal = new Services(ServiceEnum.Dyeing,17,"normal hair");
            Services dyeingLong = new Services(ServiceEnum.Dyeing,20,"long hair");
            Services dryingShort = new Services(ServiceEnum.Drying,10,"short hair");
            Services dryingNormal = new Services(ServiceEnum.Drying,15,"normal hair");
            Services dryingLong = new Services(ServiceEnum.Drying,20,"long hair");
            Services washingNormal = new Services(ServiceEnum.Washing,15,"normal hair");
            Services washingLong = new Services(ServiceEnum.Washing,20,"long hair");

            this.serviceRepository.saveAll(List.of(trimmingMen,
                    trimmingWomen,
                    pensionersMen,
                    pensionersWomen,
                    curlsShort,
                    curlsNormal,
                    curlsLong,
                    braidingWhole,
                    braidingHalf,
                    dyeingShort,
                    dyeingNormal,
                    dyeingLong,
                    dryingShort,
                    dryingNormal,
                    dryingLong,
                    washingNormal,
                    washingLong));
        }

    }

    @Override
    public List<ServicesDTO> getAllServices() {

        List<Services> all = this.serviceRepository.findAll();

        List<ServicesDTO>servicesDTOS = new ArrayList<>();

        for(Services service:all){
            servicesDTOS.add(this.modelMapper.map(service, ServicesDTO.class));
        }

        return servicesDTOS;
    }

    private boolean areServicesInit(){
        return this.serviceRepository.count()>0;
    }
}
