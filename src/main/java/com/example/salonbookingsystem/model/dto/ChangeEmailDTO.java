package com.example.salonbookingsystem.model.dto;

import javax.validation.constraints.NotBlank;

public class ChangeEmailDTO {

    @NotBlank(message = "This field is mandatory.")
   private String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String email) {
        this.newEmail = email;
    }
}
