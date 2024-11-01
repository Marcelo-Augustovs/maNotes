package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        Usuario user = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Float id,@RequestBody String senhaAntiga,String novaSenha){
        Usuario usuario = usuarioService.updateSenha(id,senhaAntiga,novaSenha);
        return ResponseEntity.noContent().build();
    }

}
