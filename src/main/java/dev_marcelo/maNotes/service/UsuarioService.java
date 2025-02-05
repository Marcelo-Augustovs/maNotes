package dev_marcelo.maNotes.service;


import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario updateSenha(Long idUsuario,String senhaAntiga, String novaSenha){
       Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
               () -> new RuntimeException(String.format("Usuario id=%s não encontrado",idUsuario))
       );
       if(usuario.getSenha().equals(senhaAntiga)) {
           usuario.setSenha(passwordEncoder.encode(novaSenha));
       } else {
           throw new RuntimeException("a Senha anterior esta incorreta");
       }
       return usuario;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByLogin(username);
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }
}
