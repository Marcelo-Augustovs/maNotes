package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.FundosEDespesasDto;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import dev_marcelo.maNotes.service.FundosEDespesasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/saldo")
public class FundosEDespesasController {

    private final FundosEDespesasService fundosEDespesasService;

    @PostMapping
    public ResponseEntity<FundosEDespesaMensal> create(@RequestBody FundosEDespesasDto dto){
        FundosEDespesaMensal mes = fundosEDespesasService.criar(dto.mes(),dto.ano());
        return ResponseEntity.status(201).body(mes);
    }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundosEDesepesasMensal(@PathVariable Long id){
        fundosEDespesasService.deletarFundosEDespesasMensal(id);
        return ResponseEntity.noContent().build();
    }
}
