package dev_marcelo.maNotes.infra.security.exceptions;

public class ApiDeleteException extends RuntimeException{
    public ApiDeleteException(String message){
        super(message);
    }
}
