package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.mapper.AnotacoesMapper;
import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.service.AnotacoesService;
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

    @PostMapping
    public ResponseEntity<AnotacoesResponseDto> create(@RequestBody AnotacoesCreateDto dto){
        Anotacoes anotacoes = AnotacoesMapper.toAnotacoes(dto);
        anotacoesService.save(anotacoes);
        return ResponseEntity.status(201).body(AnotacoesMapper.toDto(anotacoes));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AnotacoesResponseDto> updateText(@PathVariable Long id, @RequestBody Map<String,String> novoTexto){
        Anotacoes notes = anotacoesService.updateText(id,novoTexto.get("anotacao"));
        return ResponseEntity.status(200).body(AnotacoesMapper.toDto(notes));
    }

    @GetMapping
    public ResponseEntity<List<AnotacoesResponseDto>> findAllAnotacoes(){
        List<Anotacoes> listaDeAnotacao = anotacoesService.findAllAnotacoes();
        return ResponseEntity.ok().body(AnotacoesMapper.toListDto(listaDeAnotacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnotacoesResponseDto> findAnotacoes(@PathVariable Long id){
        Anotacoes anotacao = anotacoesService.findAnotacao(id);
        return ResponseEntity.ok().body(AnotacoesMapper.toDto(anotacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnotacao(@PathVariable Long id){
        Anotacoes anotacao = anotacoesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
