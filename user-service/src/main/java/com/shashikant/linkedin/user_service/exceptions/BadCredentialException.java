package com.shashikant.linkedin.user_service.exceptions;

public class BadCredentialException extends RuntimeException{

    public BadCredentialException(String message){
        super(message);
    }
}
