package dev_marcelo.maNotes.infra.security.exceptions;

public class PasswordInvalidException extends RuntimeException{

    public PasswordInvalidException(String message){
        super(message);
    }
}
