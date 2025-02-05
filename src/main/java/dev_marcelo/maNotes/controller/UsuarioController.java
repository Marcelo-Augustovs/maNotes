package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.UsuarioSenhaDto;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto){
        Usuario usuario = usuarioService.updateSenha(id,dto.getSenhaAntiga(),dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> lista = usuarioService.getAllUsers();
        return ResponseEntity.status(200).body(lista);
    }
}
