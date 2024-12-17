package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.UsuarioLoginDto;
import dev_marcelo.maNotes.dto.UsuarioSenhaDto;
import dev_marcelo.maNotes.dto.mapper.UsuarioMapper;
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
    public ResponseEntity<Usuario> create(@RequestBody UsuarioLoginDto dto){
        Usuario usuario = UsuarioMapper.toUsuario(dto);
        usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Float id, @RequestBody UsuarioSenhaDto dto){
        Usuario usuario = usuarioService.updateSenha(id,dto.getSenhaAntiga(),dto.getNovaSenha());
        return ResponseEntity.noContent().build();
    }

}
