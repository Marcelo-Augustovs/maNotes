package dev_marcelo.maNotes.infra.security.exceptions;

public class ApiCreateException extends RuntimeException{

    public ApiCreateException(String message){
        super(message);
    }
}
