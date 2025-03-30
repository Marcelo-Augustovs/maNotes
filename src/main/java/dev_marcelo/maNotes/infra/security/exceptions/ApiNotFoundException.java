package dev_marcelo.maNotes.infra.security.exceptions;

public class ApiNotFoundException extends RuntimeException{

    public ApiNotFoundException(String message){
        super(message);
    }
}
