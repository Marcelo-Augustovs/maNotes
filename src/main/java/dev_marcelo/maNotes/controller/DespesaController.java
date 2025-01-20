package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.dto.mapper.DespesaMapper;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.service.DespesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/despesa")
public class DespesaController {

    private final DespesaService despesaService;

    @PostMapping
    public ResponseEntity<Despesa> create(@RequestBody DespesaDto dto){
        Despesa despesa = despesaService.salvar(DespesaMapper.toDespesa(dto));
        return ResponseEntity.status(201).body(despesa);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DespesaDto> update(@PathVariable Long id,@RequestBody DespesaDto despesa){
        Despesa forUpdate = despesaService.updateDespesa(id,despesa);
        return ResponseEntity.status(200).body(DespesaMapper.toDto(forUpdate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesa> findById(@PathVariable Long id){
        Despesa despesa = despesaService.findAcountById(id);
        return ResponseEntity.ok(despesa);
    }

    @GetMapping()
    public ResponseEntity<List<Despesa>> findAll(){
        List<Despesa> listaDeDespesa = despesaService.findAllDespesas();
        return ResponseEntity.ok(listaDeDespesa);
    }

    @PatchMapping()
    public ResponseEntity<Void> updateStatusConta(@RequestBody DespesaDto dto){
        despesaService.findByNomeDaConta(dto.getNomeDaConta());
        despesaService.pagarContar(dto.getNomeDaConta());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateStatusConta(@PathVariable Long id){
        despesaService.delete(despesaService.findAcountById(id));
        return ResponseEntity.noContent().build();
    }

}
