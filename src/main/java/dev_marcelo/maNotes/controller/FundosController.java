package dev_marcelo.maNotes.controller;


import dev_marcelo.maNotes.dto.FundosValoresDto;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.service.FundosService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("api/v1/fundos")
public class FundosController {
    private final FundosService fundosService;

    @Operation(summary = "Criar um novo fundo",
            description = "Recurso para criar uma nova entrada de renda(fundos). Requer um usuario cadastrado. "
                    + "Requisição exige uso de bearer token",
    security = @SecurityRequirement(name = "security"),
    responses = {
            @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = Fundos.class))),
            @ApiResponse(responseCode = "401",description = "É necessario esta logado para continuar com a operação",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Usuario já cadastrado no sistema",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
    })
    @PostMapping
    public ResponseEntity<Fundos> create(@RequestBody Fundos fundos){
        fundosService.save(fundos);
        return ResponseEntity.status(201).body(fundos);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Fundos> updateValores(@PathVariable Long id, @RequestBody FundosValoresDto dto){
        Fundos fundos = fundosService.updateValor(id,dto.getOrigemDoFundo(),dto.getValorRecebido());
        return ResponseEntity.ok().body(fundos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fundos> findFundos(@PathVariable Long id){
        return ResponseEntity.ok().body(fundosService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Fundos>> findAllFundos(){
        return ResponseEntity.ok().body(fundosService.findAllFundos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundos(@PathVariable Long id){
        fundosService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
