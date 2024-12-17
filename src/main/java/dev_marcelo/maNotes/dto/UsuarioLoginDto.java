package dev_marcelo.maNotes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UsuarioLoginDto {
    @NotBlank
    private String login;
    @NotBlank
    private String senha;
}
