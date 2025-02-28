package dev_marcelo.maNotes.dto;

import java.time.LocalDate;


public record LembreteDto(Long id,String nomeDoEvento, LocalDate diaMarcado) {
}
