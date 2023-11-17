package com.example.salonbookingsystem.vallidation.anotations;

import com.example.salonbookingsystem.vallidation.ValidDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface ValidDate {

    String message()default "Date must be in future.";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
