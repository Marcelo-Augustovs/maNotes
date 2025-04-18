package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.dto.mapper.DespesaMapper;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.DespesaService;
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
@RequestMapping("api/v1/despesa")
public class DespesaController {

    private final DespesaService despesaService;

    @Operation(summary = "Criar uma despesa",
            description = "Recurso para criar uma nova despesa. Requer um usuario cadastrado. "
                    + "Requisição exige uso de bearer token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Despesa.class))),
                    @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s) Inválido(s)",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<Despesa> create(@RequestBody DespesaDto dto){
        Despesa despesa = despesaService.salvar(DespesaMapper.toDespesa(dto));
        return ResponseEntity.status(201).body(despesa);
    }

    @Operation(summary = "Atualizar despesa",description = "Requisição exige um Bearer Token.Acesso Restrito a ADMIN|USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "despesa atualizada com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = DespesaDto.class)))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<DespesaDto> update(@PathVariable Long id,@RequestBody DespesaDto despesa){
        Despesa forUpdate = despesaService.updateDespesa(id,despesa);
        return ResponseEntity.status(200).body(DespesaMapper.toDto(forUpdate));
    }

    @Operation(summary = "Listar todas despesas",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todas despesas cadatrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Despesa.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Despesa> findById(@PathVariable Long id){
        Despesa despesa = despesaService.findAcountById(id);
        return ResponseEntity.ok(despesa);
    }

    @Operation(summary = "Listar todas as despesas",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todas as desepesas cadatradas",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Despesa.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping()
    public ResponseEntity<List<Despesa>> findAll(){
        List<Despesa> listaDeDespesa = despesaService.findAllDespesas();
        return ResponseEntity.ok(listaDeDespesa);
    }

    @Operation(summary = "Atualizar o status da conta para pago",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "modifica o status pendente da conta para pago",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema()))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatusConta(@PathVariable Long id){
        despesaService.pagarContar(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "deletar despesa",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "deleta a despesa utilizando o id",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Despesa.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Long id){
        despesaService.delete(despesaService.findAcountById(id));
        return ResponseEntity.noContent().build();
    }

}
