package com.shashikant.linkedin.user_service.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){

    }

    public ResourceNotFoundException(String message){
    super(message);
    }
}
