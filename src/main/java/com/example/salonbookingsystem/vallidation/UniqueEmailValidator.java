package com.example.salonbookingsystem.vallidation;



import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.vallidation.anotations.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    private final UserRepository userRepository;

    @Autowired
    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        return this.userRepository.findByEmail(s).isEmpty();
    }
}
