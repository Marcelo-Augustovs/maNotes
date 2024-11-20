package dev_marcelo.maNotes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class AnotacoesResponseDto {
    private Float id;
    private String anotacao;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataModificacao;
}
