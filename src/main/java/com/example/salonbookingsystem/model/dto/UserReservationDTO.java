package com.example.salonbookingsystem.model.dto;


public class UserReservationDTO {

    private long id;

    private String comment;


    private String dateAndHour;


    private String additionalWashing;

    private String service;

    private double price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(String dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public String getAdditionalWashing() {
        return additionalWashing;
    }

    public void setAdditionalWashing(String additionalWashing) {
        this.additionalWashing = additionalWashing;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
