package dev_marcelo.maNotes.infra.security.exceptions;

public class DespesaNotFoundException extends RuntimeException{

    public DespesaNotFoundException(String message) {
        super(message);
    }
}
