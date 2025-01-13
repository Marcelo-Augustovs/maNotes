package dev_marcelo.maNotes.infra.security.exceptions;

public class FundosNotFoundException extends RuntimeException{
    public FundosNotFoundException(String message) {
        super(message);
    }
}
