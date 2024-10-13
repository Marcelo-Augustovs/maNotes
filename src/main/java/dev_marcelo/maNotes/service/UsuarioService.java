package dev_marcelo.maNotes.service;


import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario updateSenha(Usuario usuario){
         return usuario;
    }


}
