package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.mapper.AnotacoesMapper;
import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.AnotacoesService;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/anotacoes")
public class AnotacoesController {
    private final AnotacoesService anotacoesService;

    @Operation(summary = "Criar uma nova anotação",
    description = "Recurso para criar uma nova anotação. Requer um usuario cadastrado. "
    + "Requisição exige uso de bearer token",
    security = @SecurityRequirement(name = "security"),
    responses = {
        @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AnotacoesResponseDto.class))),
        @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping
    public ResponseEntity<AnotacoesResponseDto> create(@RequestBody AnotacoesCreateDto dto){
        Anotacoes anotacoes = AnotacoesMapper.toAnotacoes(dto);
        anotacoesService.save(anotacoes);
        return ResponseEntity.status(201).body(AnotacoesMapper.toDto(anotacoes));
    }

    @Operation(summary = "Atualizar texto",description = "Requisição exige um Bearer Token.Acesso Restrito a ADMIN|USER",
    security = @SecurityRequirement(name = "security"),
    responses = {
            @ApiResponse(responseCode = "204",description = "anotação atualizada com sucesso",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AnotacoesResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<AnotacoesResponseDto> updateText(@PathVariable Long id, @RequestBody Map<String,String> novoTexto){
        Anotacoes notes = anotacoesService.updateText(id,novoTexto.get("anotacao"));
        return ResponseEntity.status(200).body(AnotacoesMapper.toDto(notes));
    }

    @Operation(summary = "Listar todas anotações cadastradas",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todas anotações cadatrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = AnotacoesResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<AnotacoesResponseDto>> findAllAnotacoes(){
        List<Anotacoes> listaDeAnotacao = anotacoesService.findAllAnotacoes();
        return ResponseEntity.ok().body(AnotacoesMapper.toListDto(listaDeAnotacao));
    }

    @Operation(summary = "Listar todas anotações cadastradas",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todas anotações cadatrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = AnotacoesResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<AnotacoesResponseDto> findAnotacoes(@PathVariable Long id){
        Anotacoes anotacao = anotacoesService.findAnotacao(id);
        return ResponseEntity.ok().body(AnotacoesMapper.toDto(anotacao));
    }

    @Operation(summary = "deleta anotação",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "deleta anotação por id",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = AnotacoesResponseDto.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnotacao(@PathVariable Long id){
        Anotacoes anotacao = anotacoesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
