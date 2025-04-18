package dev_marcelo.maNotes.dto.autenticacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsuarioSenhaDto {
    private String senhaAntiga;
    private String novaSenha;
}
