package com.example.salonbookingsystem.vallidation;

import com.example.salonbookingsystem.vallidation.anotations.ValidDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        LocalDateTime localDateTime = convertDate(s);

        return localDateTime.isAfter(LocalDateTime.now());
    }
    private LocalDateTime convertDate(String date){

        String pattern = "yyyy-MM-dd'T'HH:mm";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);

        return LocalDateTime.parse(date, formatter);

    }
}
