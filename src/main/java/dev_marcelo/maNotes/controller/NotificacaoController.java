package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.entity.Notificacao;
import dev_marcelo.maNotes.service.NotificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notificacao")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<Notificacao> criar(@RequestBody Notificacao notificacao){
        notificacaoService.create(notificacao);
        return ResponseEntity.status(201).body(notificacao);
    }

    @GetMapping
    public ResponseEntity<List<Notificacao>> findAll(){
      return ResponseEntity.ok().body(notificacaoService.buscarTodasNotificacao());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        notificacaoService.delete(id);
        return ResponseEntity.status(204).build();
    }
}
