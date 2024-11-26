package dev_marcelo.maNotes.config.auth;


import dev_marcelo.maNotes.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UsuarioDetails extends User {

    private Usuario usuario;


    public UsuarioDetails(Usuario usuario) {
        super(usuario.getLogin(), usuario.getSenha(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
    }


    public String getUsername(){
        return this.usuario.getLogin();
    }

    public Float getId(){
        return this.usuario.getId();
    }

    public String getRole(){
        return this.usuario.getRole().name();
    }

}
