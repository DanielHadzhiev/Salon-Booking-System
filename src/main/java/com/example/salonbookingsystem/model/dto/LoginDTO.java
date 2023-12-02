package com.example.salonbookingsystem.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @Email(message = "Email should contain @ in the middle.")
    @NotBlank(message = "This field is mandatory.")
    private String email;

    @NotBlank(message = "This field is mandatory.")
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
