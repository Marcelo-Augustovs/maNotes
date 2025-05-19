package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.fundos_despesa.FundosEDespesasDto;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.FundosEDespesasService;
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
@RequestMapping("api/v1/saldo")
public class FundosEDespesasController {

    private final FundosEDespesasService fundosEDespesasService;

    @Operation(summary = "Criar uma relato dos fundos e despesa mensal",
            description = "Recurso para criar uma nova Fundos e despesa mensal. Requer um usuario cadastrado. "
                    + "Requisição exige uso de bearer token",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = FundosEDespesaMensal.class))),
                    @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<FundosEDespesaMensal> create(@RequestBody FundosEDespesasDto dto){
        FundosEDespesaMensal mes = fundosEDespesasService.criar(dto.mes(),dto.ano());
        return ResponseEntity.status(201).body(mes);
    }

    @Operation(summary = "Listar Todos os Fundos e Despesas Mensal",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "Lista com todos os Fundos e Despesas Mensais cadastrados",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = FundosEDespesaMensal.class)))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping
    public ResponseEntity<List<FundosEDespesaMensal>> getAll(){
        List<FundosEDespesaMensal> lista = fundosEDespesasService.findAll();
        return ResponseEntity.ok().body(lista);
    }

    @PatchMapping()
    public ResponseEntity<FundosEDespesaMensal> update(@RequestBody FundosEDespesasDto dto){
        FundosEDespesaMensal fundosEDespesaMensalAtualizado = fundosEDespesasService.atualizarMes(dto.mes(),dto.ano());
        return ResponseEntity.ok().body(fundosEDespesaMensalAtualizado);
    }

    @Operation(summary = "deleta um resumo dos Fundos e depesa mensal",description = "Requisição exige um Bearer Token. Acesso Restrito a ADMIN/USER",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204",description = "deleta o resumo mensal por id",
                            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema()))),
                    @ApiResponse(responseCode = "401", description = "É necessario esta logado para continuar com a operação",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundosEDesepesasMensal(@PathVariable Long id){
        fundosEDespesasService.deletarFundosEDespesasMensal(id);
        return ResponseEntity.noContent().build();
    }
}
