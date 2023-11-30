package com.example.salonbookingsystem.controllers;

import com.example.salonbookingsystem.exceptions.ForbiddenException;
import com.example.salonbookingsystem.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

   @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(){
       return "404";
   }

  @ExceptionHandler(ForbiddenException.class)
  public String handleForbidden(){
       return "403";
  }

   @ExceptionHandler(Exception.class)
    public String handleDefaultException(){
       return "error";
   }

}
