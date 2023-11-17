package com.example.salonbookingsystem.model.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDTO {

    @NotBlank(message = "This field is mandatory.")
    private String oldPassword;

    @NotBlank(message = "This field is mandatory.")
    private String newPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
