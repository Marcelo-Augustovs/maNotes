package dev_marcelo.maNotes.infra.security.exceptions;

public class UsuarioNotFoundException extends RuntimeException{
    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
