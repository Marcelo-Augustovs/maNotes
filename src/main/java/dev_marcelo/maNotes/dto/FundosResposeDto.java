package dev_marcelo.maNotes.dto;

import java.time.LocalDate;

public record FundosResposeDto(String origemDoFundo, Float valorRecebido, LocalDate dataModificacao) {
}
