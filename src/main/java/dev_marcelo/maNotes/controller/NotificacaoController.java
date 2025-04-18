package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.entity.Notificacao;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.NotificacaoService;
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
@RequestMapping("api/v1/notificacao")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @Operation(summary = "Criar uma notificacao ",
            description = "Recurso para criar uma Lembrete. Requer um usuario cadastrado. "
                    + "Requisição exige uso de bearer token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Notificacao.class))),
                    @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Usuario já cadastrado no sistema",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<Notificacao> criar(@RequestBody Notificacao notificacao){
        notificacaoService.create(notificacao);
        return ResponseEntity.status(201).body(notificacao);
    }

    @Operation(summary = "Listar todas as notificacoes agendadas",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todas as notificacao cadatradas",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Notificacao.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<Notificacao>> findAll(){
      return ResponseEntity.ok().body(notificacaoService.buscarTodasNotificacao());
    }

    @Operation(summary = "deleta a notificacao",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "deleta anotação por id",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema()))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        notificacaoService.delete(id);
        return ResponseEntity.status(204).build();
    }
}
