package dev_marcelo.maNotes.infra.security.exceptions;

public class AnotacaoNotFoundException extends RuntimeException{

    public AnotacaoNotFoundException(String message) {
        super(message);
    }
}
