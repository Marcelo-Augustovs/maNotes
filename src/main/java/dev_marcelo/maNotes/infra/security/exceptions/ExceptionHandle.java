package dev_marcelo.maNotes.infra.security.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionHandle {

    private final MessageSource messageSource;

    @ExceptionHandler(AnotacaoNotFoundException.class)
    public ResponseEntity<ErrorMessage> anotacaoNotFoundException(AnotacaoNotFoundException ex, HttpServletRequest request){
        log.error("Api Error - ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));
    }

    @ExceptionHandler(DespesaNotFoundException.class)
    public ResponseEntity<ErrorMessage> despesaNotFoundException(DespesaNotFoundException ex, HttpServletRequest request){
        log.error("Api Error - ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));
    }

    @ExceptionHandler(FundosNotFoundException.class)
    public ResponseEntity<ErrorMessage> fundosNotFoundException(FundosNotFoundException ex, HttpServletRequest request){
        log.error("Api Error - ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<ErrorMessage> usuarioNotFoundException(UsuarioNotFoundException ex, HttpServletRequest request){
        log.error("Api Error - ");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request,HttpStatus.NOT_FOUND,ex.getMessage()));
    }
}
