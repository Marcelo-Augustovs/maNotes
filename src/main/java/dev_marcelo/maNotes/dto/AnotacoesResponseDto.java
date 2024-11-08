package dev_marcelo.maNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class AnotacoesResponseDto {
    private Float id;
    private String anotacao;
    private LocalDateTime dataModificacao;
}
