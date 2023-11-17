package com.example.salonbookingsystem.model.dto;

import javax.validation.constraints.NotEmpty;

public class ImportNewsDTO {

    @NotEmpty(message = "This field is mandatory.")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
