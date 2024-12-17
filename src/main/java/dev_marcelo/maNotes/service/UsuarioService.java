package dev_marcelo.maNotes.service;


import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario save(Usuario usuario){
        try {
            usuario.setRole(Usuario.Role.USER);
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException ex){
            throw new RuntimeException(String.format("Username %s Ja foi cadastrado",usuario.getLogin()));
        }
    }



    @Transactional
    public Usuario updateSenha(Float idUsuario,String senhaAntiga, String novaSenha){
       Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(
               () -> new RuntimeException(String.format("Usuario id=%s não encontrado",idUsuario))
       );
       if(usuario.getSenha().equals(senhaAntiga)) {
           usuario.setSenha(novaSenha);
       } else {
           throw new RuntimeException("a Senha anterior esta incorreta");
       }
       return usuario;
    }
    @Transactional
    public Usuario mudarNickname(Float id,String novoNickname){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setNickname(novoNickname);
        return usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByLogin(username);
    }
}
