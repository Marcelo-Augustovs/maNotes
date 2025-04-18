package dev_marcelo.maNotes.dto.fundos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class FundosValoresDto {
    private String origemDoFundo;
    private Double valorRecebido;
}
