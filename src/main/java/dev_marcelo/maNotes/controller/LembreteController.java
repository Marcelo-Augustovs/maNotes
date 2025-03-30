package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.LembreteService;
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
@RequestMapping("api/v1/calendario")
public class LembreteController {
    private final LembreteService lembreteService;

    @Operation(summary = "Criar um Lembrete ",
            description = "Recurso para criar uma Lembrete. Requer um usuario cadastrado. "
                    + "Requisição exige uso de bearer token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = LembreteDto.class))),
                    @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Usuario já cadastrado no sistema",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
            })
    @PostMapping
    public ResponseEntity<LembreteDto> criar(@RequestBody LembreteDto dto){
       lembreteService.create(dto);
       return ResponseEntity.status(201).body(dto);
    }

    @Operation(summary = "Atualizar lembrete",description = "Requisição exige um Bearer Token.Acesso Restrito a ADMIN|USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "Lembrete atualizado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = LembreteDto.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Lembrete> update(@PathVariable Long id,@RequestBody LembreteDto dto){
        Lembrete lembreteAtualizado = lembreteService.update(id, dto.nomeDoEvento());
        return ResponseEntity.status(200).body(lembreteAtualizado);
    }

    @Operation(summary = "Listar todos os lembretes",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todos os lembretes cadatradas",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Lembrete.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<Lembrete>> getAll(){
        List<Lembrete> lista = lembreteService.findAll();
        return ResponseEntity.ok().body(lista);
    }

    @Operation(summary = "deletar Lembrete",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "deleta um lembrete utilizando o id",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Lembrete.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id){
        lembreteService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
