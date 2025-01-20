package dev_marcelo.maNotes.controller;


import dev_marcelo.maNotes.dto.FundosValoresDto;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.service.FundosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/fundos")
public class FundosController {
    private final FundosService fundosService;

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
