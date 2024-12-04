package dev_marcelo.maNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DespesaDto {
    private String nomeDaConta;
    private Float valorDaConta;
}
