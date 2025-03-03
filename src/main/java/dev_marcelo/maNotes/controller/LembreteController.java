package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.service.LembreteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/calendario")
public class LembreteController {
    private final LembreteService lembreteService;

    @PostMapping
    public ResponseEntity<LembreteDto> criar(@RequestBody LembreteDto dto){
       lembreteService.create(dto);
       return ResponseEntity.status(201).body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Lembrete> update(@PathVariable Long id,@RequestBody LembreteDto dto){
        Lembrete lembreteAtualizado = lembreteService.update(id, dto.nomeDoEvento());
        return ResponseEntity.status(200).body(lembreteAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<Lembrete>> getAll(){
        List<Lembrete> lista = lembreteService.findAll();
        return ResponseEntity.ok().body(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id){
        lembreteService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
