package dev_marcelo.maNotes.dto.fundos;

import java.time.LocalDate;

public record FundosResposeDto(String origemDoFundo, Float valorRecebido, LocalDate dataModificacao) {
}
