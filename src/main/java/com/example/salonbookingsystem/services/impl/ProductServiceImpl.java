package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.entity.Product;
import com.example.salonbookingsystem.model.enums.ServiceEnum;
import com.example.salonbookingsystem.repositories.ProductRepository;
import com.example.salonbookingsystem.repositories.ServiceRepository;
import com.example.salonbookingsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ServiceRepository serviceRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ServiceRepository serviceRepository) {
        this.productRepository = productRepository;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void initializeProducts() {

if(!areProductsInit()) {
    Product shampoo = new Product("shampoo",
            100,
            this.serviceRepository.findAllByServiceName(ServiceEnum.Washing));
    Product hairDye = new Product("hair dye",
            100,
            this.serviceRepository.findAllByServiceName(ServiceEnum.Dyeing));
    Product hairBands = new Product("hair bands",
            100,
            this.serviceRepository.findAllByServiceName(ServiceEnum.Braiding));

    this.productRepository.saveAll(List.of(shampoo,
            hairDye,
            hairBands));
}

    }

    private boolean areProductsInit(){
        return this.productRepository.count()>0;
    }
}
