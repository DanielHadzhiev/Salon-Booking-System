package com.example.salonbookingsystem.model.dto;

public class ServicesDTO {

    private long id;

    private String serviceName;

    private String clientFeature;

    public String getServiceName() {
        return serviceName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClientFeature() {
        return clientFeature;
    }

    public void setClientFeature(String clientFeature) {
        this.clientFeature = clientFeature;
    }
}
