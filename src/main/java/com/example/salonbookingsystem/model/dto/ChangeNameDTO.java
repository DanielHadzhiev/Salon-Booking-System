package com.example.salonbookingsystem.model.dto;

import javax.validation.constraints.NotBlank;

public class ChangeNameDTO {

    @NotBlank(message = "This field is mandatory.")
    private String newName;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
