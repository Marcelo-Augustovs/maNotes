package dev_marcelo.maNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FundosValoresDto {
    private String origemDoFundo;
    private Float valorRecebido;
}
