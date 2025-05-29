package dev_marcelo.maNotes.service;


import dev_marcelo.maNotes.dto.usuario.UsuarioResponseDto;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.infra.security.exceptions.PasswordInvalidException;
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
               () -> new ApiNotFoundException(String.format("Usuario id=%s não encontrado",idUsuario))
       );
       if(passwordEncoder.matches(senhaAntiga, usuario.getSenha())) {
           usuario.setSenha(passwordEncoder.encode(novaSenha));
       } else {
           throw new PasswordInvalidException("a Senha anterior esta incorreta");
       }
       return usuario;
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByLogin(username);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new ApiNotFoundException(String.format("Usuario id=%s não encontrado",id))
        );
    }
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> getAllUsers() {
        List<Usuario> usuarioList = usuarioRepository.findAll();
        List<UsuarioResponseDto> dtoList = usuarioList.stream()
                .map(u -> new UsuarioResponseDto(u.getId(),u.getLogin(),u.getRole()))
                .toList();
        return dtoList;
    }
}
