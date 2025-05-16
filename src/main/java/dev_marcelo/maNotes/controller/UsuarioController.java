package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.autenticacao.UsuarioSenhaDto;
import dev_marcelo.maNotes.dto.usuario.UsuarioResponseDto;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Atualizar senha",description = "Requisição exige um Bearer Token.Acesso Restrito a ADMIN|USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AnotacoesResponseDto.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto){
        Usuario usuario = usuarioService.updateSenha(id,dto.getSenhaAntiga(),dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os usuarios",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todos os usuarios cadatradas",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Usuario.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<UsuarioResponseDto> lista = usuarioService.getAllUsers();
        return ResponseEntity.status(200).body(lista);
    }
}
