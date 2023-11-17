package com.example.salonbookingsystem.model.dto;

import com.example.salonbookingsystem.vallidation.anotations.UniqueEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterDTO {

@NotNull(message = "Please select a picture to upload.")
    private MultipartFile userImage;
@NotBlank(message = "This field is mandatory.")
    private String name;
@Email(message = "Email should contain @ in the middle.")
@UniqueEmail
@NotBlank(message = "This field is mandatory.")
    private String email;

@NotBlank(message = "This field is mandatory.")
    private String password;

@NotBlank(message = "This field is mandatory.")
    private String gender;

    public MultipartFile getUserImage() {
        return userImage;
    }

    public void setUserImage(MultipartFile userImage) {
        this.userImage = userImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "userImage=" + userImage +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
