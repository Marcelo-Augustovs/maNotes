package dev_marcelo.maNotes.infra.security.exceptions;

public class LembretesNotFoundException extends RuntimeException{
    public LembretesNotFoundException(String message) {
        super(message);
    }
}
