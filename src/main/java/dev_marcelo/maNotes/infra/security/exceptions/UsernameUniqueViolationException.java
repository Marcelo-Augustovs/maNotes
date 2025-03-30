package dev_marcelo.maNotes.infra.security.exceptions;

public class UsernameUniqueViolationException extends RuntimeException{
    public UsernameUniqueViolationException(String message){
        super(message);
    }
}
