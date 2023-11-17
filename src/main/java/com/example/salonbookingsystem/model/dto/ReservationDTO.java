package com.example.salonbookingsystem.model.dto;


import com.example.salonbookingsystem.vallidation.anotations.ValidDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ReservationDTO {
    @NotBlank(message = "This field is mandatory.")
    private String comment;

@ValidDate
    @NotNull(message = "This field is mandatory.")
    private String dateAndHour;

     @NotNull(message = "This field is mandatory.")
    private String selectedService;


    private boolean additionalWashing;

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

    public String getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(String service) {
        this.selectedService = service;
    }

    public boolean getAdditionalWashing() {
        return additionalWashing;
    }

    public void setAdditionalWashing(boolean additionalWashing) {
        this.additionalWashing = additionalWashing;
    }
}
