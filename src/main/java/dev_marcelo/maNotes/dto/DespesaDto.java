package dev_marcelo.maNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DespesaDto {
    private String nomeDaConta;
    private Double valorDaConta;
    private String category;
    private String payment;
    private LocalDate dataModificacao;
}
