package com.example.salonbookingsystem.model.entity;

import com.example.salonbookingsystem.model.enums.ServiceEnum;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class Services {

    public Services(ServiceEnum serviceName, double price, String clientFeature) {
        this.serviceName = serviceName;
        this.price = price;
        this.clientFeature = clientFeature;
    }

    public Services() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "service_name")
    @Enumerated(EnumType.STRING)
    private ServiceEnum serviceName;

    @Column(name = "price")
    private double price;

   @Column(name = "client_feature")
   private String clientFeature;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientFeature() {
        return clientFeature;
    }

    public void setClientFeature(String clientFeature) {
        this.clientFeature = clientFeature;
    }

    public ServiceEnum getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceEnum serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
